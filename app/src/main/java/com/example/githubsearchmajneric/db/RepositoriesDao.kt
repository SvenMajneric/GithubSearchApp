package com.example.githubsearchmajneric.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubsearchmajneric.model.SearchedRepository

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchedRepo(searchedRepository: SearchedRepository)

    @Query("SELECT * FROM SearchedRepository")
    fun getSavedRepos(): LiveData<List<SearchedRepository>>

    @Query("SELECT * FROM SearchedRepository WHERE repoName = :repoName")
    suspend fun getSpecificSavedRepo(repoName: String) : SearchedRepository

    @Delete
    suspend fun deleteSavedRepo(repo: SearchedRepository)

    @Query("DELETE FROM SearchedRepository")
    suspend fun deleteAll()

}