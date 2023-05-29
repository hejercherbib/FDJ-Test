package com.hejercherbib.fdj.android.di

import com.hejercherbib.fdj.android.domain.repositories.LeagueRepository
import com.hejercherbib.fdj.android.domain.repositories.LeagueRepositoryImpl
import com.hejercherbib.fdj.android.domain.repositories.TeamRepository
import com.hejercherbib.fdj.android.domain.repositories.TeamRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTeamRepository(impl: TeamRepositoryImpl): TeamRepository
    @Binds
    abstract fun bindLeagueRepository(impl: LeagueRepositoryImpl): LeagueRepository
}