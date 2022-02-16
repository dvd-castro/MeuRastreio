package br.com.davidcastro.meurastreio.view.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.ErrorEnum
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.ActivityMainBinding
import br.com.davidcastro.meurastreio.databinding.DialogAdicionarCodigoBinding
import br.com.davidcastro.meurastreio.helpers.utils.AlarmReceiver
import br.com.davidcastro.meurastreio.helpers.utils.showSnackbar
import br.com.davidcastro.meurastreio.view.adapters.ViewPagerAdapter
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog : DialogAdicionarCodigoBinding
    private lateinit var alertDialog : AlertDialog
    private var nome: String = ""
    private var codigo: String = ""

    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingDialog = DialogAdicionarCodigoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getAllTracking()
        initUi()
        initObservers()
        initAlarmManager(this)
    }

    private fun initObservers() {
        viewModel.ifTrackingExists.observe(this, ::whenVerifyIfTrackingExists)
        viewModel.findResult.observe(this, ::whenFindResult)
        viewModel.insertSucess.observe(this, ::whenInsertSucess)
        viewModel.error.observe(this, ::onError)
        viewModel.loader.observe(this, ::onLoader )
    }

    private fun initUi() {
        configFab()
        configViewPager()
        configDialog()
    }

    private fun configFab() {
        binding.fab.setOnClickListener {
            alertDialog.show()
            setDialogTextActionColor()
            setDialogCancelListener()
        }
    }

    private fun initAlarmManager(context: Context) {

        alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        alarmManager?.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            1000 * 60 * 30,
            alarmIntent
        )
    }

    private fun configViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(fragmentActivity = this)
        configTabLayout()
    }

    private fun configTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_item_em_andamento)
                else -> tab.text = getString(R.string.tab_item_concluidos)
            }
        }.attach()
    }

    private fun configDialog() {
        alertDialog = this.let {

            val builder = AlertDialog.Builder(it)

            builder.apply {

                setTitle(getString(R.string.title_adicionar_novo_rastreio))
                setView(bindingDialog.root)

                setPositiveButton(getString(R.string.action_to_add)) { dialog, _ ->

                    codigo = bindingDialog.codigo.text.toString()
                    nome = bindingDialog.nome.text.toString()

                    if(codigo.isNotEmpty() || codigo.isNotBlank()) {
                        verifyIfTrackingExists(codigo)
                    } else {
                        showSnackbar(binding.root, getString(R.string.error_campo_vazio))
                    }
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, _ ->
                    dialog.cancel()
                }
            }

            builder.create()
        }
    }

    private fun setDialogCancelListener() {
        alertDialog.setOnCancelListener {
            bindingDialog.nome.setText("")
            bindingDialog.codigo.setText("")
        }
    }

    private fun setDialogTextActionColor() {
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.red))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.green))
    }

    private fun onLoader(boolean: Boolean){
        if (boolean)
            binding.loader.root.isVisible = boolean
        else
            binding.loader.root.isVisible = boolean
    }

    private fun onError(int: Int) {
        when(int) {
            ErrorEnum.ERROR_NAO_ENCONTRADO.id -> showSnackbar(binding.root, getString(R.string.error_rastreio_nao_encontrado))
            ErrorEnum.ERROR_INSERIDO.id -> showSnackbar(binding.root, getString(R.string.error_rastreio_ja_inserido))
            ErrorEnum.ERROR_NETWORK.id -> showSnackbar(binding.root, getString(R.string.error_network))
            else -> showSnackbar(binding.root, getString(R.string.error_server))
        }
    }

    private fun verifyIfTrackingExists (codigo: String) {
        viewModel.verifyIfTrackingExists(codigo)
    }

    private fun findTracking(codigo: String) {
        viewModel.findTracking(codigo)
    }

    private fun insertTracking(nome: String, codigo: String, eventos: List<EventosModel>) {
        val tracking = RastreioModel(nome, codigo, eventos)
        viewModel.insertTracking(tracking)
    }

    private fun getAllTracking(){
        viewModel.getAllTracking().invokeOnCompletion {
            reload()
        }
    }

    private fun reload(){
        viewModel.reload()
    }

    private fun whenFindResult(tracking: RastreioModel) {
        insertTracking(nome, tracking.codigo, tracking.eventos)
    }

    private fun whenInsertSucess(isSucess: Boolean) {
        if(isSucess) {
            getAllTracking()
            resetSavedFieldValues()
        }
    }

    private fun whenVerifyIfTrackingExists(exists: Boolean) {
        if (!exists) {
            findTracking(codigo)
        } else {
            onError(ErrorEnum.ERROR_INSERIDO.id)
            resetSavedFieldValues()
        }
    }

    private fun resetSavedFieldValues() {
        codigo = ""
        nome = ""
    }
}