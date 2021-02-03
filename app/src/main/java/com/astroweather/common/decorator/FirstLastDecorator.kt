package com.astroweather.common.decorator

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import com.astroweather.R

class FirstLastDecorator(
    @DimenRes val start: Int = R.dimen._0dp,
    @DimenRes val top: Int = R.dimen._0dp,
    @DimenRes val end: Int = R.dimen._0dp,
    @DimenRes val bottom: Int = R.dimen._0dp
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val childPosition = parent.getChildAdapterPosition(view)
        if (childPosition == 0) {
            outRect.left = view.resources.getDimensionPixelSize(start)
            outRect.top = view.resources.getDimensionPixelSize(top)
        }
        if (childPosition == state.itemCount - 1) {
            outRect.right = view.resources.getDimensionPixelSize(end)
            outRect.bottom = view.resources.getDimensionPixelSize(bottom)
        }
    }
}