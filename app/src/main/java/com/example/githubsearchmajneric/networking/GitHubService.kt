package com.example.githubsearchmajneric.networking

import com.example.githubsearchmajneric.model.GithubRepositorySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    suspend fun searchForRepositories(@Query("q") repositoryName: String): Response<GithubRepositorySearchResponse>

    @GET("search/repositories")
    suspend fun searchForRepositoriesSorted(@Query("q") repositoryName: String, @Query("sort") sortParameter: String, @Query("per_page") numberOfResponses: Int, @Query("page") numberOfPages: Int): Response<GithubRepositorySearchResponse>
}