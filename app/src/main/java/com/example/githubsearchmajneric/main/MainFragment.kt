package com.example.githubsearchmajneric.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.githubsearchmajneric.R
import com.example.githubsearchmajneric.databinding.FragmentMainBinding
import com.example.githubsearchmajneric.model.SearchedRepository
import com.example.githubsearchmajneric.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding : FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val viewModel : MainScreenViewModel by activityViewModels()

    private val mainScreenAdapter = MainScreenAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)


        binding.rvRepos.adapter = mainScreenAdapter

        setupListeners()
        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setupListeners(){
        binding.btnSearch.setOnClickListener {
            fetchRepositories()
        }

        mainScreenAdapter.navigationListener = object  : MainScreenAdapter.NavigationInteractionListener{
            override fun onItemClicked(savedRepo: SearchedRepository) {
                viewModel.setDetailRepoData(savedRepo.repoName)
                findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
            }

        }
        mainScreenAdapter.browserNavigationListener = object  : MainScreenAdapter.NavigationInteractionListener{
            override fun onItemClicked(savedRepo: SearchedRepository) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + savedRepo.authorName))
                startActivity(intent)
            }

        }
    }

    private fun bind(){
        viewModel.getSavedRepos().observe(viewLifecycleOwner, {
            setData(it)
        })

        viewModel.repositoriesData.observe(viewLifecycleOwner, { response ->


            when(response){
                is Resource.Success -> {
                    response.data?.let { responseModel ->
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }

                is Resource.Error -> {
                    response.message.let { message ->
                        Log.e("MainActivity", "An error has occured $message")
                    }

                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.i("MainActivity", "I'm loading the content")
                }

            }
        })
    }

    private fun setData(repositories: List<SearchedRepository>){
        mainScreenAdapter.setData(repositories)
    }

    private fun fetchRepositories(){
        val searchedRepository = binding.etSearch.text.toString()
        val sortParameter = binding.spSortBy.selectedItem.toString().lowercase(Locale.getDefault())

        if (searchedRepository.isNotBlank()){
            viewModel.fetchRepositoryWithName(searchedRepository, sortParameter)
        }
    }



}