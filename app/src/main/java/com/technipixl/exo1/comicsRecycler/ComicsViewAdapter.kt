package com.technipixl.exo1.comicsRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.ComicsCellBinding
import com.technipixl.network.ComicsList

class ComicsViewAdapter(private val comicsList :ComicsList, private val clickListener :OnSelectItem) :RecyclerView.Adapter<ComicsViewHolder>() {
    private lateinit var binding :ComicsCellBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        binding = ComicsCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comicsList.comicList.size
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(comicsList.comicList[position], clickListener)
    }

    interface OnSelectItem{
        fun onItemClicked(id :String)
    }
}