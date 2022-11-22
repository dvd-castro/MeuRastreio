package br.com.davidcastro.trackingdetails.view.adapters

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.davidcastro.data.model.Evento
import br.com.davidcastro.trackingdetails.R
import br.com.davidcastro.trackingdetails.databinding.LayoutListItemDetailsBinding
import br.com.davidcastro.ui.utils.UiUtils

class TrackingDetailsAdapter(private val list: List<Evento>): Adapter<TrackingDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingDetailsViewHolder =
        TrackingDetailsViewHolder(TrackingDetailsViewHolder.inflateViewBinding(parent))


    override fun onBindViewHolder(holder: TrackingDetailsViewHolder, position: Int) {
        holder.bind(list[position], position, isLastItem(position))
    }

    override fun getItemCount(): Int = list.count()

    private fun isLastItem(position: Int): Boolean = position == itemCount - 1
}

class TrackingDetailsViewHolder(private val binding: LayoutListItemDetailsBinding): ViewHolder(binding.root) {

    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): LayoutListItemDetailsBinding =
            LayoutListItemDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    private val context = binding.root.context

    fun bind(item: Evento, position: Int, lastItem: Boolean) {
        setStatus(item)
        setSubstatus(item)
        setDate(item)
        setDot(position)
        removeLastLine(lastItem)
    }

    private fun removeLastLine(lastItem: Boolean) {
        if (lastItem) {
            binding.flLine.visibility = View.GONE
        }
    }

    private fun setDot(position: Int) {
        if(position == 0) {
            binding.flEllipseColored.visibility = View.VISIBLE
        }
    }

    private fun setDate(item: Evento) {
        binding.tvUpdatedAt.text = item.getEventDateAndHourAndLocal()
        binding.tvUpdatedAt.visibility = View.VISIBLE
    }
    private fun setStatus(item: Evento) {
        item.status?.let {
            binding.tvStatus.text = item.status
            binding.tvStatus.visibility = View.VISIBLE
            binding.tvStatus.setTextColor(UiUtils.getTrackingStatusColor(context, it))
        }
    }

    private fun setSubstatus(item: Evento) {
        if(!item.subStatus.isNullOrEmpty()) {
            item.subStatus?.let {
                when(it.count()) {
                    1 -> {
                        setIfHasImportedMessage(binding.tvSubstatusOne, it.first())
                    }
                    2 -> {
                        setIfHasImportedMessage(binding.tvSubstatusOne, it[0])
                        setIfHasImportedMessage(binding.tvSubstatusTwo, it[1])
                    }
                }
            }
        }
    }

    private fun setIfHasImportedMessage(textView: AppCompatTextView, subStatus: String) {
        textView.visibility = View.VISIBLE
        if(subStatus.contains("Minhas Importações")) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = UiUtils.getHtmlString(context.getString(R.string.message_acessar_importacoes))
        } else {
            textView.text = subStatus
        }
    }
}