package com.technipixl.exo1.characterRecycler

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.exo1.R
import com.technipixl.exo1.databinding.CharacterCellBinding
import com.technipixl.network.Character

class CharactersViewHolder(private var viewBinding :CharacterCellBinding) :RecyclerView.ViewHolder(viewBinding.root){

    fun bind(character :Character, clickListener :CharactersViewAdapter.OnSelectItem){
        val avatarUrl = character.thumbnail?.let { "${it.path}.${it.extension}" }
        avatarUrl?.let {setupAvatar(it)}
        viewBinding.characterName.text = character.name

        itemView.setOnClickListener {
            clickListener.onItemClicked(character.id!!)
        }
    }

    private fun setupAvatar(url :String){
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(viewBinding.avatar)
    }
}