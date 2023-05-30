package com.hejercherbib.fdj.android.di

import android.content.Context
import androidx.room.Room
import com.hejercherbib.fdj.android.data.dataSources.local.AppDatabase
import com.hejercherbib.fdj.android.data.dataSources.local.LeagueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fdj.db"
        ).build()
    }

    @Provides
    fun provideLeagueDao(appDatabase: AppDatabase): LeagueDao {
        return appDatabase.leagueDao()
    }
}
