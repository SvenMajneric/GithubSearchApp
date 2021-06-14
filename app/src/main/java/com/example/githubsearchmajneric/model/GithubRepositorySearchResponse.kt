package com.example.githubsearchmajneric.model

data class GithubRepositorySearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)