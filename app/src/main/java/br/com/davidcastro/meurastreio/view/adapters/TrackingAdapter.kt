package br.com.davidcastro.meurastreio.view.adapters

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.tvCodigo.text = item.code
        binding.cvItemList.setOnClickListener { listener.onItemClick(tracking = item) }
        setDate(item)
        setStatus(item)
        setSubstatus(item)
        setHasCompletedAndHasUpdated(item)
        setName(item.name)
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
                binding.tvSubstatusOne.text = it.first()
                binding.tvSubstatusOne.visibility = View.VISIBLE

                if(it.count() == 2) {
                    binding.tvSubstatusTwo.visibility = View.VISIBLE
                    if(it[1].contains("Minhas Importações")) {
                        binding.tvSubstatusTwo.movementMethod = LinkMovementMethod.getInstance()
                        binding.tvSubstatusTwo.text = UiUtils.getHtmlString(context.getString(R.string.message_acessar_importacoes))
                    } else {
                        binding.tvSubstatusTwo.text = it[1]
                    }
                }
            }
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