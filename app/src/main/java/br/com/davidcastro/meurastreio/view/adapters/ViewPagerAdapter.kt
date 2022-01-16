package br.com.davidcastro.meurastreio.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.davidcastro.meurastreio.view.fragments.ConcluidosFragment
import br.com.davidcastro.meurastreio.view.fragments.EmAndamentoFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> EmAndamentoFragment()
            else -> ConcluidosFragment()
        }
    }

}