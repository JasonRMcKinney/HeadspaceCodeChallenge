package com.example.headspacecodechallenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headspacecodechallenge.R
import com.example.headspacecodechallenge.db.entities.ImageEntry
import com.example.headspacecodechallenge.ui.listener.ImageItemClickListener

class ImageAdapter(
    val images: MutableList<ImageEntry>,
    private val imageItemClickListener: ImageItemClickListener
) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindItem(images[position], imageItemClickListener)
    }

}