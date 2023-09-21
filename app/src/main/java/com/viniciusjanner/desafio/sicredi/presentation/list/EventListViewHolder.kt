package com.viniciusjanner.desafio.sicredi.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.viniciusjanner.desafio.sicredi.databinding.ItemEventListBinding
import com.viniciusjanner.desafio.sicredi.framework.imageloader.ImageLoader
import com.viniciusjanner.desafio.sicredi.presentation.common.GenericViewHolder
import com.viniciusjanner.desafio.sicredi.util.extensions.formatDateHour

class EventListViewHolder(
    itemBinding: ItemEventListBinding,
    private val imageLoader: ImageLoader,
) : GenericViewHolder<EventItem>(itemBinding) {

    private val eventImage: ImageView = itemBinding.eventImage
    private val eventTitle: TextView = itemBinding.eventTitle
    private val eventDateHour: TextView = itemBinding.eventDateHour
    private val eventSubtitle: TextView = itemBinding.eventSubtitle

    @SuppressLint("SetTextI18n")
    override fun bind(data: EventItem) {
        imageLoader.load(eventImage, data.image!!)
        eventTitle.text = data.title
        eventDateHour.text = data.date?.formatDateHour()
        eventSubtitle.text = data.description
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
        ): EventListViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val itemBinding = ItemEventListBinding.inflate(inflater, parent, false)

            return EventListViewHolder(itemBinding, imageLoader)
        }
    }
}
