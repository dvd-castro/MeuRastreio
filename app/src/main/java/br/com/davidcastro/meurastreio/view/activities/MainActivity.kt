package br.com.davidcastro.meurastreio.view.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.ActivityMainBinding
import br.com.davidcastro.meurastreio.databinding.DialogAdicionarCodigoBinding
import br.com.davidcastro.meurastreio.view.adapters.ViewPagerAdapter
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog : DialogAdicionarCodigoBinding
    private lateinit var alertDialog : AlertDialog
    private var nome: String = ""
    private var codigo: String = ""

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingDialog = DialogAdicionarCodigoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getAllTracking()
        initUi()
        initObservers()
    }

    private fun initObservers() {
        viewModel.ifTrackingExists.observe(this, ::whenVerifyIfTrackingExists)
        viewModel.findResult.observe(this, ::whenFindResult)
        viewModel.insertSucess.observe(this, ::whenInsertSucess)
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
        }
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

    private fun configDialog(){
        alertDialog = this.let {

            val builder = AlertDialog.Builder(it)

            builder.apply {

                setTitle(getString(R.string.title_adicionar_novo_rastreio))
                setView(bindingDialog.root)

                setPositiveButton(getString(R.string.action_to_add)) { dialog, _ ->

                    codigo = bindingDialog.codigo.text.toString()
                    nome = bindingDialog.nome.text.toString()

                    if(codigo.isNotEmpty() || codigo.isNotBlank()){
                        verifyIfTrackingExists(codigo)
                    } else{
                        //TODO mostrar mensagem de campo vazio
                    }

                    dialog.cancel()
                }

                setNegativeButton(getString(R.string.action_to_cancel)) { dialog, _ ->
                    dialog.cancel()
                }

            }

            builder.setOnCancelListener {
                bindingDialog.nome.setText("")
                bindingDialog.codigo.setText("")
            }

            builder.create()
        }
    }

    private fun setDialogTextActionColor(){
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.red))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.green))
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
        viewModel.getAllTracking()
    }

    private fun whenFindResult(tracking: RastreioModel){
        insertTracking(nome, tracking.codigo, tracking.eventos)
    }

    private fun whenInsertSucess(isSucess: Boolean) {
        if(isSucess) {
            getAllTracking()
        } else {
            // TODO mensagem de erro ao inserir
        }
    }

    private fun whenVerifyIfTrackingExists(exists: Boolean) {
        if (!exists) {
            findTracking(codigo)
        } else {
            // TODO mostrar mensagem de que já existe
            resetSavedFieldValues()
        }
    }

    private fun resetSavedFieldValues(){
        codigo = ""
        nome = ""
    }
}