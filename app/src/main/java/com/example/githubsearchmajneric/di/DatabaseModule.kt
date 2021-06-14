package com.example.githubsearchmajneric.di

import android.content.Context
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import com.example.githubsearchmajneric.db.AppDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "GithubApp")
            .build()
    }
}