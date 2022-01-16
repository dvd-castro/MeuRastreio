package br.com.davidcastro.meurastreio.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.com.davidcastro.meurastreio.R
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

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingDialog = DialogAdicionarCodigoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()
    }

    private fun initUI(){
        configFab()
        configViewPager()
        configDialog()
    }

    private fun configFab(){
        binding.fab.setOnClickListener {
            alertDialog.show()
        }
    }

    private fun configViewPager(){
        binding.viewPager.adapter = ViewPagerAdapter(fragmentActivity = this)
        configTabLayout()
    }

    private fun configTabLayout(){
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
                setTitle(getString(R.string.title_add_a_new_tracking))
                setView(bindingDialog.root)

                setPositiveButton(getString(R.string.action_to_add)) { _, _ ->
                    //TODO adicionar o novo rastreio
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
}