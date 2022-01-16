package br.com.davidcastro.meurastreio.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.databinding.ListItemDetalhesBinding
import br.com.davidcastro.meurastreio.data.model.EventosModel

class DetalhesViewHolder(private val binding: ListItemDetalhesBinding) : RecyclerView.ViewHolder(binding.root) {
    lateinit var context: Context

    companion object {
        internal fun inflateViewBinding(parent: ViewGroup, viewType: Int): ListItemDetalhesBinding {
            return ListItemDetalhesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    }

    fun bind(item: EventosModel){
        binding.model = item
        context = binding.root.context

        verificarStatus(item)
    }

    private fun verificarStatus(item : EventosModel){

        when(item.status){

            context.getString(R.string.status_postato_apos_horario_limite) -> {
                binding.status.setTextColor(context.getColor(R.color.red))
                binding.imageView.setBackgroundResource(R.drawable.outline_info)
            }

            context.getString(R.string.status_encaminhado) -> {
                binding.status.setTextColor(context.getColor(R.color.blue))
                binding.imageView.setBackgroundResource(R.drawable.outline_forward)
            }

            context.getString(R.string.status_saiu_para_entrega) -> {
                binding.status.setTextColor(context.getColor(R.color.orange))
                binding.imageView.setBackgroundResource(R.drawable.outline_schedule)
            }

            context.getString(R.string.status_entregue) -> {
                binding.status.setTextColor(context.getColor(R.color.green))
                binding.imageView.setBackgroundResource(R.drawable.outline_check_circle)
            }

            else -> {
                binding.status.setTextColor(context.getColor(R.color.orange))
                binding.imageView.setBackgroundResource(R.drawable.outline_schedule)
            }
        }
    }
}