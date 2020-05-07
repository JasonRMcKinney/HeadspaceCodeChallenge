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
        val dimensionText = image.height.toString() + " x " + image.width.toString()
        itemView.imageDimensions.text = dimensionText
        //load image
        itemView.image.load(image.download_url)
        itemView.setOnClickListener {
            if (itemView.imageAuthor.visibility == View.GONE) {
                itemView.imageAuthorTitle.visibility = View.VISIBLE
                itemView.imageAuthor.visibility = View.VISIBLE
                itemView.imageDimensions.visibility = View.VISIBLE
            } else {
                itemView.imageAuthorTitle.visibility = View.GONE
                itemView.imageAuthor.visibility = View.GONE
                itemView.imageDimensions.visibility = View.GONE
            }
        }
    }
}