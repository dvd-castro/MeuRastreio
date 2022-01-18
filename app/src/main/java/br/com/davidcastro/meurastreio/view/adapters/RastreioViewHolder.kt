package br.com.davidcastro.meurastreio.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.RastreioModel
import br.com.davidcastro.meurastreio.databinding.ListItemRastreioBinding
import br.com.davidcastro.meurastreio.view.listeners.ClickListener

class RastreioViewHolder(private val binding : ListItemRastreioBinding, private val listener: ClickListener) : RecyclerView.ViewHolder(binding.root) {

    lateinit var context: Context

    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): ListItemRastreioBinding {
            return ListItemRastreioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    }

    fun bind(item: RastreioModel){
        binding.model = item
        context = binding.root.context

        binding.container.setOnClickListener {
            listener.onItemClick(item.codigo)
        }

        verificarStatus(item)
    }

    private fun verificarStatus(item : RastreioModel){
        val eventos = item.eventos

        when(eventos[0].status){

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