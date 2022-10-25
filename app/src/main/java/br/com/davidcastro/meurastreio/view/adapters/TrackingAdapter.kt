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
        with(binding) {
            tvCodigo.text = item.code
            tvData.text = item.getEventDateAndLocal()

            setStatus(item)
            setLogistic(item)
            setHasCompleted(item.hasCompleted)
            setName(item.name)
        }
    }

    private fun setStatus(item: TrackingModel) {
        item.getLastEvent().status?.let {
            binding.tvStatus.text = it
            binding.tvStatus.setTextColor(UiUtils.getColor(binding.root.context, it))
        }
    }

    private fun setLogistic(item: TrackingModel) {
        if(!item.getLastEvent().subStatus.isNullOrEmpty()) {
            binding.tvLogistica.text = item.getLastEventDestiny()
            binding.tvLogistica.visibility = View.VISIBLE
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