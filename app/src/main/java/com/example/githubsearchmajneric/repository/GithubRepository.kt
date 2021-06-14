package com.example.githubsearchmajneric.repository

import androidx.lifecycle.LiveData
import com.example.githubsearchmajneric.db.AppDatabase
import com.example.githubsearchmajneric.model.SearchedRepository
import com.example.githubsearchmajneric.networking.GitHubService
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val api: GitHubService,
    private val database: AppDatabase
) {

    private val repositoriesDao = database.repositoriesDao()

    suspend fun fetchSearchedRepositories(searchQuery: String) = api.searchForRepositories(searchQuery)

    suspend fun fetchSortedRepositories(searchQuery: String, sortParameter: String) = api.searchForRepositoriesSorted(searchQuery, sortParameter, 8, 1)

    suspend fun saveRepository(searchedRepo: SearchedRepository){
        repositoriesDao.saveSearchedRepo(searchedRepo)
    }

    fun getSavedRepos(): LiveData<List<SearchedRepository>>{
        return repositoriesDao.getSavedRepos()
    }
    suspend fun getSpecificSavedRepo(searchedRepoName: String): SearchedRepository{
        return repositoriesDao.getSpecificSavedRepo(searchedRepoName)
    }

    suspend fun deleteAll(){
        repositoriesDao.deleteAll()
    }
}