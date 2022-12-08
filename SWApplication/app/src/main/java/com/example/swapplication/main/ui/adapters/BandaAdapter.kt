package com.example.swapplication.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.swapplication.databinding.BandaListItemBinding
import com.example.swapplication.models.Banda

class BandaAdapter (val listener: BandaListener) :
    ListAdapter<
            Banda,
            BandaAdapter.ViewHolder
            >(BandaDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    class ViewHolder private constructor(
        val binding: BandaListItemBinding,
        val listener: BandaListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Banda, position: Int) {
            binding.apply {
                bandaNome.text = item.nomeBanda
                bandaGenero.text = item.generorBanda
                bandaFormacao.text = item.anoBanda

                ivEdit.setOnClickListener {
                    listener.onEditClick(item)
                }
                ivDelete.setOnClickListener {
                    listener.onDeleteClick(item)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: BandaListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BandaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class BandaDiffCallback : DiffUtil.ItemCallback<Banda>() {

    override fun areItemsTheSame(oldItem: Banda, newItem: Banda): Boolean {
        return oldItem.nomeBanda == newItem.nomeBanda
    }

    override fun areContentsTheSame(oldItem: Banda, newItem: Banda): Boolean {
        return oldItem == newItem
    }
}


interface BandaListener {
    fun onEditClick(banda: Banda)
    fun onDeleteClick(banda: Banda)
}