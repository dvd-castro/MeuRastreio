package br.com.davidcastro.meurastreio.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.home.view.fragments.HomeFragment
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    private lateinit var alertDialogRequest : AlertDialog
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        initAdMob()

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commitNow()
        }

        initSharedPreferences()
        configDialogStartOnBootRequest()
        enableAutoStartIfXiaomiDevice()
    }

    private fun initAdMob() {
        MobileAds.initialize(this)
    }

    private fun initSharedPreferences() {
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        editor = preferences.edit()
    }

    private fun enableAutoStartIfXiaomiDevice() {
        if(Build.MANUFACTURER == getString(R.string.xiaomi_manufacturer) && !preferences.contains(AUTO_START_REQUESTED)) {
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

    companion object {
        const val AUTO_START_REQUESTED = "auto_start_enable"
    }
}