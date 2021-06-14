package com.example.githubsearchmajneric.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearchmajneric.databinding.ItemRepositoryBinding
import com.example.githubsearchmajneric.model.SearchedRepository

class MainScreenAdapter: RecyclerView.Adapter<MainScreenAdapter.MainViewHolder>() {

    private val items: MutableList<SearchedRepository> = mutableListOf()

    var navigationListener: NavigationInteractionListener? = null
    var browserNavigationListener: NavigationInteractionListener? = null


    private val _selectedItem: MutableLiveData<SearchedRepository> = MutableLiveData()
    var selectedItem: LiveData<SearchedRepository> = _selectedItem

    interface NavigationInteractionListener{
        fun onItemClicked(savedRepo: SearchedRepository)
    }

    inner class MainViewHolder(internal val itemBinding: ItemRepositoryBinding): RecyclerView.ViewHolder(itemBinding.root){
        init {
            itemBinding.root.setOnClickListener {
                _selectedItem.postValue(items[layoutPosition])
                navigationListener?.onItemClicked(items[layoutPosition])
            }
            itemBinding.ivAuthorImage.setOnClickListener {

                browserNavigationListener?.onItemClicked(items[layoutPosition])
            }
        }

        fun bind(savedRepo: SearchedRepository){
            itemBinding.tvAuthorName.text = savedRepo.authorName
            itemBinding.tvForksNumber.text = "Forks ${savedRepo.forks}"
            itemBinding.tvIssuesNumber.text = "Issues  ${savedRepo.issues}"
            itemBinding.tvRepoName.text = savedRepo.repoName
            itemBinding.tvWatchersNumber.text = "Watchers ${savedRepo.watchers}"
        }

    }

    fun setData(searchedRepos: List<SearchedRepository>){
        items.clear()
        items.addAll(searchedRepos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemBinding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            Glide
                .with(this.itemView)
                .load(items[position].authorImageUrl)
                .into(itemBinding.ivAuthorImage)
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}