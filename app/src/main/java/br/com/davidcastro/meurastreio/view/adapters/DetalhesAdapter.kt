package br.com.davidcastro.meurastreio.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.meurastreio.data.model.EventosModel

class DetalhesAdapter(private val data: List<EventosModel>): RecyclerView.Adapter<DetalhesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalhesViewHolder {
        val binding = DetalhesViewHolder.inflateViewBinding(parent, viewType)

        return DetalhesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetalhesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}