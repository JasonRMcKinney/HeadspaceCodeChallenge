package com.example.headspacecodechallenge.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.headspacecodechallenge.db.entities.ImageEntry
import kotlinx.android.synthetic.main.item_photo.view.*

class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(image: ImageEntry) {
        //populate text
        itemView.imageAuthor.text = image.author
        itemView.imageDimensions.text = "${image.height} x ${image.width}"
        //load image
        itemView.image.load(image.download_url)
    }
}