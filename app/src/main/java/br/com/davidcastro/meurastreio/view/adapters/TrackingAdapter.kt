package br.com.davidcastro.meurastreio.view.adapters

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.meurastreio.R
import br.com.davidcastro.meurastreio.view.listeners.ClickListener
import br.com.davidcastro.ui.databinding.LayoutListItemRastreioBinding
import br.com.davidcastro.ui.utils.UiUtils

class TrackingAdapter(private val listener: ClickListener): ListAdapter<TrackingModel, TrackingViewHolder>(DiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingViewHolder =
        TrackingViewHolder(TrackingViewHolder.inflateViewBinding(parent))

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) = holder.bind(getItem(position), listener)
}

class TrackingViewHolder(private val binding: LayoutListItemRastreioBinding): RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): LayoutListItemRastreioBinding =
            LayoutListItemRastreioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    fun bind(item: TrackingModel, listener: ClickListener) {
        setName(item)
        setClickListener(item, listener)
        setDate(item)
        setStatus(item)
        setSubstatus(item)
        setHasCompletedAndHasUpdated(item)
        setName(item.name)
    }

    private fun setName(item: TrackingModel) {
        binding.tvCodigo.text = item.code
    }

    private fun setClickListener(item: TrackingModel, listener: ClickListener) {
        binding.cvItemList.setOnClickListener { listener.onItemClick(tracking = item) }
    }

    private fun setDate(item: TrackingModel) {
        if(item.events.isNotEmpty()) {
            binding.tvData.text = item.getEventDateAndLocal()
        }
    }

    private fun setStatus(item: TrackingModel) {
        if(item.events.isNotEmpty()) {
            item.getLastEvent().status?.let {
                binding.tvStatus.text = it
                binding.tvStatus.setTextColor(UiUtils.getTrackingStatusColor(context, it))
            }
        }
    }

    private fun setSubstatus(item: TrackingModel) {
        if(item.events.isNotEmpty() && !item.getLastEvent().subStatus.isNullOrEmpty()) {
            item.getLastEvent().subStatus?.let {
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
            textView.text = UiUtils.getHtmlString(context.getString(br.com.davidcastro.trackingdetails.R.string.message_acessar_importacoes))
        } else {
            textView.text = subStatus
        }
    }

    private fun setHasCompletedAndHasUpdated(item: TrackingModel) {
        if(item.hasCompleted) {
            binding.cvHasCompletedOrUpdated.visibility = View.VISIBLE
            binding.tvHasCompleted.text = "Finalizado"
        } else if(item.hasUpdated) {
            binding.cvHasCompletedOrUpdated.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))
            binding.cvHasCompletedOrUpdated.visibility = View.VISIBLE
            binding.tvHasCompleted.text = "Atualizado"
        }
    }

    private fun setName(name: String?) {
        if(name?.isNotEmpty() == true) {
            binding.tvTitle.text = name
            binding.tvTitle.visibility = View.VISIBLE
        }
    }
}

object DiffUtil: DiffUtil.ItemCallback<TrackingModel>() {
    override fun areItemsTheSame(oldItem: TrackingModel, newItem: TrackingModel) =
        newItem.code == oldItem.code

    override fun areContentsTheSame(oldItem: TrackingModel, newItem: TrackingModel) =
        newItem.getLastEventDate() == oldItem.getLastEventDate()

}