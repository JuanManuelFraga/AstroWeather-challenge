package com.astroweather.chooser.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.astroweather.chooser.model.ChooserModel
import kotlinx.android.synthetic.main.chooser_view_holder.view.*

class ChooserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(chooserModel: ChooserModel, onRowClick: (cityId: Int?) -> Unit) = with(view) {
        flagIcon.setImageResource(chooserModel.icon)
        cityName.text = resources.getString(chooserModel.nameRes)
        this.setOnClickListener {
            onRowClick(chooserModel.cityId)
        }
    }
}