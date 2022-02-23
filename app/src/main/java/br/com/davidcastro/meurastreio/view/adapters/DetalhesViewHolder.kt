package br.com.davidcastro.meurastreio.view.adapters

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.data.model.EventosModel
import br.com.davidcastro.meurastreio.databinding.ListItemDetalhesBinding

class DetalhesViewHolder(private val binding: ListItemDetalhesBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var context: Context

    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): ListItemDetalhesBinding {
            return ListItemDetalhesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
    }

    fun bind(item: EventosModel) {
        context = binding.root.context

        setData(item)
        verificarStatus(item)
    }

    private fun setData(item: EventosModel) {
        binding.model = item

        val subStatus = item.subStatus
        if(subStatus.isNotEmpty()) {
            configIfHaveHtmlText(subStatus.first())
        } else {
            binding.substatus.visibility = View.GONE
        }
    }

    private fun configIfHaveHtmlText(subStatus: String) {
        if(subStatus.contains(">Minhas Importações<")) {
            binding.substatus.text = HtmlCompat.fromHtml(context.getString(R.string.message_acessar_importacoes), HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.substatus.movementMethod = LinkMovementMethod.getInstance()
        } else {
            binding.substatus.text = subStatus
        }
    }

    private fun verificarStatus(item : EventosModel) {

        when(item.status) {

            context.getString(R.string.status_postado_apos_horario_limite) -> {
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