package com.example.swapplication.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.swapplication.databinding.BandaListItemBinding
import com.example.swapplication.models.BandaComId

class BandaComIdAdapter(val listener: BandaComIdListener) :
    ListAdapter<
            BandaComId,
            BandaComIdAdapter.ViewHolder
            >(BandaComIdDiffCallback()) {

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
        val listener: BandaComIdListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BandaComId, position: Int) {
            binding.apply {
                bandaNome.text = item.nomeBanda
                bandaGenero.text = item.generoBanda
                bandaFormacao.text = item.formacaoBanda

                ivEdit.setOnClickListener {
                    listener.onEditClick(item)
                }
                ivDelete.setOnClickListener {
                    listener.onDeleteClick(item)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: BandaComIdListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BandaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class BandaComIdDiffCallback : DiffUtil.ItemCallback<BandaComId>() {

    override fun areItemsTheSame(oldItem: BandaComId, newItem: BandaComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BandaComId, newItem: BandaComId): Boolean {
        return oldItem == newItem
    }
}


interface BandaComIdListener {
    fun onEditClick(banda: BandaComId)
    fun onDeleteClick(banda: BandaComId)
}