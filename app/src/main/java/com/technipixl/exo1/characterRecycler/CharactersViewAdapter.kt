package com.technipixl.exo1.characterRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.exo1.databinding.CharacterCellBinding
import com.technipixl.network.CharactersResults

class CharactersViewAdapter(private val characters :CharactersResults, private val clickListener :OnSelectItem) :RecyclerView.Adapter<CharactersViewHolder>() {
    private lateinit var binding :CharacterCellBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        binding = CharacterCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characters.results.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters.results[position], clickListener)
    }

    interface OnSelectItem{
        fun onItemClicked(id :Int)
    }
}