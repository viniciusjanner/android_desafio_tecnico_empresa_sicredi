package com.viniciusjanner.desafio.core.domain.model

interface ItemDiff {

    val key: Long

    fun areItemsTheSame(other: ItemDiff) = this.key == other.key

    fun areContentsTheSame(other: ItemDiff) = this == other
}
