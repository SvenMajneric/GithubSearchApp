package com.example.githubsearchmajneric.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.githubsearchmajneric.R
import com.example.githubsearchmajneric.databinding.FragmentDetailBinding
import com.example.githubsearchmajneric.main.MainScreenAdapter
import com.example.githubsearchmajneric.main.MainScreenViewModel
import com.example.githubsearchmajneric.model.SearchedRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel : MainScreenViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners(searchedRepo: SearchedRepository){
        binding.ivAuthorImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + searchedRepo.authorName))
            startActivity(intent)
        }
        binding.tvRepoName.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + "${searchedRepo.authorName}/" + searchedRepo.repoName))
            startActivity(intent)
        }
    }

    private fun bind(){
        viewModel.selectedItem.observe(viewLifecycleOwner){ searchedRepo ->
            showInfo(searchedRepo)
            setListeners(searchedRepo)
        }
    }

    private fun showInfo(searchedRepo: SearchedRepository) {
        setListeners(searchedRepo)
        binding.apply {
            tvAuthorName.text = searchedRepo.authorName
            tvRepoName.text = searchedRepo.repoName
            tvForksNumber.text = "Forks\n${searchedRepo.forks}"
            tvWatchersNumber.text = "Watchers\n${searchedRepo.watchers}"
            tvIssuesNumber.text = "Issues\n${searchedRepo.issues}"
            tvDateCreation.text = "Created at\n${searchedRepo.createdAt}"
            tvLastUpdate.text = "Updated at\n${searchedRepo.updatedAt}"
            tvProgrammingLanguage.text = searchedRepo.language
            Glide.with(this@DetailFragment)
                .load(searchedRepo.authorImageUrl)
                .into(ivAuthorImage)
        }
    }


}