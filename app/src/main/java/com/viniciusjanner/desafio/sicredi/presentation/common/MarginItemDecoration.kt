package com.viniciusjanner.desafio.sicredi.presentation.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private var marginTop: Int,
    private var marginLeft: Int,
    private var marginRight: Int,
    private var marginBottom: Int,
    private var marginBottomLast: Int = marginBottom
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)

            // Verifica se é o primeiro item
            if (position == 0) top = marginTop

            left = marginLeft

            right = marginRight

            // Verifica se é o último item
            bottom = if (position == state.itemCount - 1) marginBottomLast else marginBottom
        }
    }
}
