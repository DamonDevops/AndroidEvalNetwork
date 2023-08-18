package com.technipixl.exo1.comicsRecycler

import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.ComicsCellBinding
import com.technipixl.network.Comic

class ComicsViewHolder(private val viewBinding :ComicsCellBinding) :RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(comic :Comic , clickListener : ComicsViewAdapter.OnSelectItem){
        viewBinding.comicName.text = comic.name

        itemView.setOnClickListener {
            val comicId = comic.URI?.split("/")?.last()
            clickListener.onItemClicked(comicId!!)
        }
    }

}