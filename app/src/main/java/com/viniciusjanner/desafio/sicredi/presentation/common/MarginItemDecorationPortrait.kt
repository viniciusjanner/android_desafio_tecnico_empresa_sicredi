package com.viniciusjanner.desafio.sicredi.presentation.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorationPortrait(
    private var marginTop: Int,
    private var marginBottom: Int,
    private var marginBottomLast: Int = marginBottom,
    private var marginRight: Int,
    private var marginLeft: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        with(outRect) {
            val position = parent.getChildAdapterPosition(view)

            // Verifica se é o primeiro item
            if (position == 0) top = marginTop

            // Verifica se é o último item
            bottom = if (position == state.itemCount - 1) marginBottomLast else marginBottom

            left = marginLeft

            right = marginRight
        }
    }
}
