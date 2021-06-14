package com.example.githubsearchmajneric.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubsearchmajneric.model.SearchedRepository

@Database(entities = [SearchedRepository::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao
}