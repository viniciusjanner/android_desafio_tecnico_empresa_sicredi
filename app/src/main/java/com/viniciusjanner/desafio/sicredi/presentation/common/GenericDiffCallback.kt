package com.viniciusjanner.desafio.sicredi.presentation.common

import androidx.recyclerview.widget.DiffUtil
import com.viniciusjanner.desafio.core.domain.model.ItemDiff

class GenericDiffCallback<T : ItemDiff> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areContentsTheSame(newItem)
}
