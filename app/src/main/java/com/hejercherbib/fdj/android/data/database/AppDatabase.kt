package com.hejercherbib.fdj.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hejercherbib.fdj.android.model.League

@Database(entities = [League::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
}
