package com.hejercherbib.fdj.android.teamsScreenTests

import com.hejercherbib.fdj.android.domain.usecases.league.GetAllLeaguesUseCase
import com.hejercherbib.fdj.android.repositoriesTests.FakeLeagueListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllLeaguesUseCaseTest {

    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase
    private lateinit var fakeLeaguesListRepository: FakeLeagueListRepository

    @Before
    fun setUp() {
        fakeLeaguesListRepository = FakeLeagueListRepository()
        getAllLeaguesUseCase = GetAllLeaguesUseCase(fakeLeaguesListRepository, Dispatchers.IO)
    }

    @Test
    fun `when fetching from repository use case should return same data`(): Unit = runBlocking {
        // Todo
    }
}
