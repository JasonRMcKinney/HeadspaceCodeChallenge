package com.example.headspacecodechallenge.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*

class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(image: ImageEntry) {
        //populate text
        itemView.imageAuthor.text = image.author
        itemView.imageDimensions.text = "${image.height} x ${image.width}"
        //load image
        Picasso.get().load(image.url).into(itemView.image)
    }
}