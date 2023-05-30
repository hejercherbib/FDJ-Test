package com.hejercherbib.fdj.android.data.dataSources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hejercherbib.fdj.android.data.dataSources.local.entities.LeagueEntity

@Dao
interface LeagueDao {

    @Query("SELECT * FROM LeagueEntity ORDER BY name")
    fun getAll(): List<LeagueEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(leagues: List<LeagueEntity>)
}
