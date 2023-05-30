package com.hejercherbib.fdj.android.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hejercherbib.fdj.android.data.dataSources.local.AppDatabase
import com.hejercherbib.fdj.android.data.dataSources.local.LeagueDao
import com.hejercherbib.fdj.android.data.dataSources.local.entities.LeagueEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var leagueDao: LeagueDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        leagueDao = db.leagueDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val league1 = LeagueEntity("21", "League1", "Foot", "fake")
        val league2 = LeagueEntity("22", "League2", "Foot", "fake")
        val league3 = LeagueEntity("23", "League3", "Foot", "fake")
        val league4 = LeagueEntity("24", "League4", "Foot", "fake")
        val fakeLeagues = listOf(league1, league2, league3, league4)

        leagueDao.insertAll(fakeLeagues)
        val allFetchedLeagues = leagueDao.getAll()
        assertTrue(allFetchedLeagues.containsAll(fakeLeagues))
    }
}
