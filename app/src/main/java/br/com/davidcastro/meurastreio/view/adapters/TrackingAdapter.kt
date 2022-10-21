package br.com.davidcastro.meurastreio.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.data.model.TrackingModel
import br.com.davidcastro.ui.databinding.LayoutListItemRastreioBinding

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
            tvData.text = item.getLastEventDate()
            tvLogistica.text = item.getLastEvent().local
            tvTitle.text = item.name
            tvStatus.text = item.getLastEvent().status
        }
    }
}
object DiffUtil: DiffUtil.ItemCallback<TrackingModel>() {
    override fun areItemsTheSame(oldItem: TrackingModel, newItem: TrackingModel) =
        newItem.code == oldItem.code

    override fun areContentsTheSame(oldItem: TrackingModel, newItem: TrackingModel) =
        newItem.getLastEventDate() == oldItem.getLastEventDate()

}