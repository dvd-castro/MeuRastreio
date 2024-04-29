package br.com.davidcastro.meurastreio.core.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.core.theme.MeuRastreioTheme
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    private lateinit var alertDialogRequest : AlertDialog
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val requestPermissionLauncher: ActivityResultLauncher<String> by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        initAdMob()
        initSharedPreferences()
        configDialogStartOnBootRequest()
        enableAutoStartIfXiaomiDevice()
        setNotificationsChannel()
        requestNotificationPermission()

        setContent {
            MeuRastreioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("Android")
                }
            }
        }
    }

    private fun initAdMob() {
        MobileAds.initialize(this)
    }

    private fun initSharedPreferences() {
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        editor = preferences.edit()
    }

    private fun enableAutoStartIfXiaomiDevice() {
        if(Build.MANUFACTURER == getString(R.string.xiaomi_manufacturer) && !preferences.contains(
                AUTO_START_REQUESTED
            )) {
            alertDialogRequest.show()
        }
    }

    private fun requestStartOnBoot() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
        startActivity(intent)
    }

    private fun configDialogStartOnBootRequest() {
        alertDialogRequest = this.let {

            val builder = AlertDialog.Builder(it)

            builder.apply {
                setTitle(getString(R.string.title_permissao_iniciar))
                setMessage(R.string.message_permissao_iniciar)
                setView(R.layout.dialog_permissao_iniciar)

                setPositiveButton(getString(R.string.action_to_config)) { _, _ ->
                    requestStartOnBoot()
                    editor.putBoolean(AUTO_START_REQUESTED, true).commit()
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, _ ->
                    dialog.cancel()
                    editor.putBoolean(AUTO_START_REQUESTED, true).commit()
                }
            }
            builder.create()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun setNotificationsChannel() {
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(getString(R.string.notification_channel_id), name, importance).apply {
            description = descriptionText
            setShowBadge(true)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val AUTO_START_REQUESTED = "auto_start_enable"
    }
}