package br.com.davidcastro.meurastreio.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.view.listeners.ClickListener

class RastreioAdapter(
    private val listener: ClickListener
): ListAdapter<RastreioModel, RastreioViewHolder>(RastreioDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RastreioViewHolder {
        val binding = RastreioViewHolder.inflateViewBinding(parent)

        return RastreioViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RastreioViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}

object RastreioDiffCallback: DiffUtil.ItemCallback<RastreioModel>() {

    override fun areItemsTheSame(oldItem: RastreioModel, newItem: RastreioModel): Boolean {
        return oldItem.codigo == newItem.codigo
    }

    override fun areContentsTheSame(oldItem: RastreioModel, newItem: RastreioModel): Boolean {
        return oldItem == newItem
    }
}