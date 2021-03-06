package com.example.githubsearchmajneric.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearchmajneric.model.GithubRepositorySearchResponse
import com.example.githubsearchmajneric.model.Item
import com.example.githubsearchmajneric.model.SearchedRepository
import com.example.githubsearchmajneric.repository.GithubRepository
import com.example.githubsearchmajneric.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val gitRepo: GithubRepository): ViewModel() {

    private val _repositoriesData : MutableLiveData<Resource<GithubRepositorySearchResponse>> = MutableLiveData()
    var repositoriesData: LiveData<Resource<GithubRepositorySearchResponse>> = _repositoriesData

    private val _selectedItem : MutableLiveData<SearchedRepository> = MutableLiveData()
    var selectedItem : LiveData<SearchedRepository> = _selectedItem




    fun getSavedRepos(): LiveData<List<SearchedRepository>> {
        return gitRepo.getSavedRepos()
    }

    suspend fun getSpecificSavedRepo(repoName: String){
        var response: SearchedRepository? = null

        viewModelScope.async(Dispatchers.IO) {
             response = gitRepo.getSpecificSavedRepo(repoName)
        }.await()


        _selectedItem.postValue(response)

    }

    private suspend fun saveRepository(repo: SearchedRepository) {
        viewModelScope.launch(Dispatchers.IO) {
            gitRepo.saveRepository(repo)
        }
    }


     suspend fun saveSortedResponses(sortedResponse: List<Item>){

        for (searchedRepo in sortedResponse){
            val repo = SearchedRepository(
                0,
                searchedRepo.name,
                searchedRepo.owner.login,
                searchedRepo.owner.avatar_url,
                searchedRepo.watchers_count,
                searchedRepo.forks,
                searchedRepo.open_issues,
                searchedRepo.language,
                searchedRepo.created_at,
                searchedRepo.updated_at
            )
            Log.d("DataInsertion", "${repo.repoName}  ${repo.forks}")
            viewModelScope.async(Dispatchers.IO) {
                saveRepository(repo)
                delay(400)
            }.await()

        }
    }

    fun fetchRepositoryWithName(searchQuery: String, sortParameter: String){
        viewModelScope.launch(Dispatchers.IO) {
            _repositoriesData.postValue(Resource.Loading())
            val response = gitRepo.fetchSortedRepositories(searchQuery, sortParameter)
//
            _repositoriesData.postValue(handleGitHubResponse(response))
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            gitRepo.deleteAll()
        }
    }

    private fun handleGitHubResponse(response: Response<GithubRepositorySearchResponse>): Resource<GithubRepositorySearchResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}