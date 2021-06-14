package com.example.githubsearchmajneric.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchedRepository(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val repoName: String = "",
    @ColumnInfo
    val authorName: String = "",
    @ColumnInfo
    val authorImageUrl: String = "",
    @ColumnInfo
    val watchers: Int = 0,
    @ColumnInfo
    val forks: Int = 0,
    @ColumnInfo
    val issues: Int = 0,
    @ColumnInfo
    val language: String = "",
    @ColumnInfo
    val createdAt: String = "",
    @ColumnInfo
    val updatedAt: String = ""
)
