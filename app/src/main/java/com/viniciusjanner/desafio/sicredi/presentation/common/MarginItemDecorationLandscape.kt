package com.viniciusjanner.desafio.sicredi.presentation.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorationLandscape(
    private var marginTop: Int,
    private var marginBottom: Int,
    private var marginRight: Int,
    private var marginRightLast: Int = marginRight,
    private var marginLeft: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        with(outRect) {
            val position = parent.getChildAdapterPosition(view)

            top = marginTop

            bottom = marginBottom

            // Verifica se é o primeiro item
            if (position == 0) left = marginLeft

            // Verifica se é o último item
            right = if (position == state.itemCount - 1) marginRightLast else marginRight
        }
    }
}
