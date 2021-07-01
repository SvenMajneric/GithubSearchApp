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
        var specificRepository: SearchedRepository? = null

        viewModelScope.async {
             specificRepository = gitRepo.getSpecificSavedRepo(repoName)
            _selectedItem.postValue(specificRepository)
        }.await()

    }

    fun setDetailRepoData(repoName: String){
        viewModelScope.launch {
            getSpecificSavedRepo(repoName)
        }
    }




    private suspend fun saveRepository(repo: SearchedRepository) = viewModelScope.async { gitRepo.saveRepository(repo) }.await()



     private suspend fun saveSortedResponses(sortedResponse: List<Item>)=
         viewModelScope.async {

             try{
                 for (searchedRepo in sortedResponse){
                     if (searchedRepo.language == null){
                         searchedRepo.language = "Not known"
                     }
                     val repo = SearchedRepository(
                         0,
                         searchedRepo.name,
                         searchedRepo.owner.login,
                         searchedRepo.owner.avatar_url,
                         searchedRepo.watchers_count,
                         searchedRepo.forks,
                         searchedRepo.open_issues,
                         searchedRepo.language!!,
                         searchedRepo.created_at,
                         searchedRepo.updated_at
                     )
                     saveRepository(repo)
                     Log.d("DataInsertion", "${repo.repoName}  ${repo.forks}")
                 }
             } catch (e: Exception){
                 Log.d("CoroutineException", e.message.toString())
             }


        }.await()


    fun fetchRepositoryWithName(searchQuery: String, sortParameter: String){
        viewModelScope.launch(Dispatchers.IO) {
            _repositoriesData.postValue(Resource.Loading())
            val response = gitRepo.fetchSortedRepositories(searchQuery, sortParameter)
            response.data?.let { githubApiResponse ->
                deleteAll()
                saveSortedResponses(githubApiResponse.items)
            }
            _repositoriesData.postValue(response)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            gitRepo.deleteAll()
        }
    }


}