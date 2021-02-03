package com.astroweather.chooser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.astroweather.R
import com.astroweather.chooser.model.ChooserModel
import com.astroweather.chooser.ui.view.ChooserViewHolder

class ChooserAdapter(
    private val onRowClick: (cityId: Int?) -> Unit
) : ListAdapter<ChooserModel, ChooserViewHolder>(createDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chooser_view_holder, parent, false)
        return ChooserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChooserViewHolder, position: Int) {
        holder.bind(currentList[position], onRowClick)
    }

    companion object {
        private fun createDiffCallback() = object : DiffUtil.ItemCallback<ChooserModel>() {
            override fun areItemsTheSame(oldItem: ChooserModel, newItem: ChooserModel): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(oldItem: ChooserModel, newItem: ChooserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}