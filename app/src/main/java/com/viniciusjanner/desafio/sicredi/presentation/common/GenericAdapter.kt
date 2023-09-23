package com.viniciusjanner.desafio.sicredi.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.viniciusjanner.desafio.core.domain.model.ItemDiff

inline fun <T : ItemDiff, VH : GenericViewHolder<T>> getGenericAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH,
): ListAdapter<T, VH> {
    val diff = GenericDiffCallback<T>()

    return object : ListAdapter<T, VH>(diff) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
            createViewHolder(parent)

        override fun onBindViewHolder(holder: VH, position: Int) =
            holder.bind(getItem(position))
    }
}
