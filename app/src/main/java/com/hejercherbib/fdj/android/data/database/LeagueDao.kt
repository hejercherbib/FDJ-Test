package com.hejercherbib.fdj.android.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hejercherbib.fdj.android.model.League

@Dao
interface LeagueDao {

    @Query("SELECT * FROM League ORDER BY strLeague")
    fun getAll(): List<League>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(leagues: List<League>)
}
