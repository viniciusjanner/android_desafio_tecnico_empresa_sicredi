package com.viniciusjanner.desafio.core.domain.model

data class Event(
    val id: String,
    val people: List<Any?>?,
    val date: Long?,
    val description: String?,
    val image: String?,
    val longitude: Double?,
    val latitude: Double?,
    val price: Double?,
    val title: String?,
    override val key: Long = id.toLong(),
) : ItemDiff
