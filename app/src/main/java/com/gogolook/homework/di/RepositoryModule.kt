package com.gogolook.homework.di

import com.gogolook.homework.model.api.ApiService
import com.gogolook.homework.model.repository.GogolookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideRoloRepository(apiService: ApiService): GogolookRepository {
        return GogolookRepository(apiService)
    }
}