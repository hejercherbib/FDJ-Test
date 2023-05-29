package com.hejercherbib.fdj.android.teamsScreenTests

import com.hejercherbib.fdj.android.domain.usecases.team.GetTeamsByLeagueUseCase
import com.hejercherbib.fdj.android.repositoriesTests.FakeTeamsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTeamsUseCaseTest {

    private lateinit var getTeamsUseCase: GetTeamsByLeagueUseCase
    private lateinit var fakeTeamsListRepository: FakeTeamsListRepository

    @Before
    fun setUp() {
        fakeTeamsListRepository = FakeTeamsListRepository()
        getTeamsUseCase = GetTeamsByLeagueUseCase(fakeTeamsListRepository, Dispatchers.IO)
    }

    @Test
    fun `Get only teams that have id of the passed league parameter`(): Unit = runBlocking {
        // Todo
    }
}
