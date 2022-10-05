package br.com.davidcastro.meurastreio.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.davidcastro.meurastreio.data.model.TrackingHome
import br.com.davidcastro.meurastreio.databinding.LayoutListItemRastreioBinding

class TrackingAdapter(private val list: List<TrackingHome>): RecyclerView.Adapter<TrackingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingViewHolder =
        TrackingViewHolder(TrackingViewHolder.inflateViewBinding(parent))

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.count()
}

class TrackingViewHolder(private val binding: LayoutListItemRastreioBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        internal fun inflateViewBinding(parent: ViewGroup): LayoutListItemRastreioBinding =
            LayoutListItemRastreioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    fun bind(item: TrackingHome) {
        with(binding) {
            tvCodigo.text = item.code
            tvData.text = item.date
            tvLogistica.text = item.local
            tvTitle.text = item.name
            tvStatus.text = item.lastStatus
        }
    }
}