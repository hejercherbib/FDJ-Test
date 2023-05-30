package com.hejercherbib.fdj.android.data.dataSources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hejercherbib.fdj.android.data.dataSources.local.entities.LeagueEntity

@Database(entities = [LeagueEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
}
