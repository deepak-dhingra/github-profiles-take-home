package com.example.githubprofile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.databinding.LayoutRepoItemBinding

class ReposAdapter(private val items: List<ProfileRepo>, private val onItemClick: (position: Int) -> Unit): RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRepoItemBinding.inflate(inflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position], position, onItemClick)
    }

    inner class RepoViewHolder(private val binding: LayoutRepoItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: ProfileRepo, position: Int, onItemClick: (position: Int) -> Unit) {
            binding.repoTitle.text = item.name
            binding.repoDescription.text = item.description
            binding.repoItem.setOnClickListener {
                onItemClick(position)
            }
        }
    }
}