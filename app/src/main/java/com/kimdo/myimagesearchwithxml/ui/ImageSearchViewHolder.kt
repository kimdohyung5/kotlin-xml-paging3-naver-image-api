package com.kimdo.myimagesearchwithxml.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimdo.myimagesearchwithxml.R
import com.kimdo.myimagesearchwithxml.databinding.ImageSearchItemBinding
import com.kimdo.myimagesearchwithxml.model.Item

class ImageSearchViewHolder(
    private val like: (Item) -> Unit,
    private val binding: ImageSearchItemBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item?) {
        item?.let {
            Glide.with(binding.root).load(it.thumbnail).centerCrop().into(binding.imageView)

            binding.imageView.setOnClickListener {
                like.invoke(item)
            }
        }
    }

    companion object {
        fun create(like: (Item) -> Unit,
                   parent: ViewGroup
        ) : ImageSearchViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.image_search_item, parent, false)
            val binding = ImageSearchItemBinding.bind(view)

//            val binding = ImageSearchItemBinding.inflate(inflater, parent, false)
            return ImageSearchViewHolder(like, binding)
        }
    }
}