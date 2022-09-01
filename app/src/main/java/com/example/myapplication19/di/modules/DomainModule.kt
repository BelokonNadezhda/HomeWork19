package com.example.myapplication19.di.modules

import com.example.myapplication19.data.MainRepository
import com.example.myapplication19.data.TmdbApi
import com.example.myapplication19.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}