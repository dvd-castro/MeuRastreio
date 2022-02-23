package br.com.davidcastro.meurastreio.view.activities

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.MessageEnum
import br.com.davidcastro.meurastreio.databinding.ActivityMainBinding
import br.com.davidcastro.meurastreio.databinding.DialogAdicionarCodigoBinding
import br.com.davidcastro.meurastreio.helpers.utils.showSnackbar
import br.com.davidcastro.meurastreio.view.adapters.ViewPagerAdapter
import br.com.davidcastro.meurastreio.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog : DialogAdicionarCodigoBinding
    private lateinit var alertDialog : AlertDialog

    private val viewModel: MainViewModel by viewModel()

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
        viewModel.message.observe(this, ::onMessage)
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

            var name: String
            var code: String

            builder.apply {

                setTitle(getString(R.string.title_adicionar_novo_rastreio))
                setView(bindingDialog.root)

                setPositiveButton(getString(R.string.action_to_add)) { dialog, _ ->

                    code = bindingDialog.code.text.toString()
                    name = bindingDialog.name.text.toString()

                    if(code.isNotEmpty() || code.isNotBlank()) {
                        viewModel.verifyIfTrackingExistsOnDb(code, name)
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
            bindingDialog.name.setText("")
            bindingDialog.code.setText("")
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

    private fun onMessage(int: Int) {
        when(int) {
            MessageEnum.NOT_FOUND.id -> showSnackbar(binding.root, getString(R.string.error_rastreio_nao_encontrado))
            MessageEnum.ALREADY_INSERTED.id -> showSnackbar(binding.root, getString(R.string.error_rastreio_ja_inserido))
            MessageEnum.NETWORK_ERROR.id -> showSnackbar(binding.root, getString(R.string.error_network))
            MessageEnum.DELETE_ERROR.id -> showSnackbar(binding.root, getString(R.string.error_deletar))
            MessageEnum.INSERTED_WITH_SUCESS.id -> {
                showSnackbar(binding.root, getString(R.string.message_inserido_com_sucesso))
                resetSavedFieldValues()
            }
            else -> showSnackbar(binding.root, getString(R.string.error_server))
        }
    }

    private fun getAllTracking(){
        viewModel.getAllTracking().invokeOnCompletion {
            viewModel.reload()
        }
    }

    private fun resetSavedFieldValues() {
        bindingDialog.code.setText("")
        bindingDialog.name.setText("")
    }
}