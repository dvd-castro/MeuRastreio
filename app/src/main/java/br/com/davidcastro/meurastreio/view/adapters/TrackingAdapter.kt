package br.com.davidcastro.meurastreio.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.ui.databinding.LayoutListItemRastreioBinding
import br.com.davidcastro.ui.utils.UiUtils

class TrackingAdapter: ListAdapter<TrackingModel, TrackingViewHolder>(DiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingViewHolder =
        TrackingViewHolder(TrackingViewHolder.inflateViewBinding(parent))

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) = holder.bind(getItem(position))
}

class TrackingViewHolder(private val binding: LayoutListItemRastreioBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): LayoutListItemRastreioBinding =
            LayoutListItemRastreioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    fun bind(item: TrackingModel) {
        binding.tvCodigo.text = item.code
        setDate(item)
        setStatus(item)
        setSubstatus(item)
        setHasCompleted(item.hasCompleted)
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
                binding.tvStatus.setTextColor(UiUtils.getTrackingStatusColor(binding.root.context, it))
            }
        }
    }

    private fun setSubstatus(item: TrackingModel) {
        if(item.events.isNotEmpty() && !item.getLastEvent().subStatus.isNullOrEmpty()) {

            item.getLastEvent().subStatus?.let {

                binding.tvSubstatusOne.text = it.first()
                binding.tvSubstatusOne.visibility = View.VISIBLE

                if(it.count() == 2) {
                    binding.tvSubstatusTwo.text = UiUtils.getHtmlString(it[1])
                    binding.tvSubstatusTwo.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setHasCompleted(hasCompleted: Boolean) {
        if(hasCompleted) {
            binding.cvHasCompleted.visibility = View.VISIBLE
            binding.tvHasCompleted.text = "Finalizado"
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