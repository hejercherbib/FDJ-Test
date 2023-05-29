package com.hejercherbib.fdj.android.ui.teamScreen

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hejercherbib.fdj.android.domain.usecases.league.GetAllLeaguesUseCase
import com.hejercherbib.fdj.android.domain.usecases.team.GetTeamsByLeagueUseCase
import com.hejercherbib.fdj.android.model.Team
import com.hejercherbib.fdj.android.repositoriesTests.FakeLeagueListRepository
import com.hejercherbib.fdj.android.repositoriesTests.FakeTeamsListRepository
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
internal class TeamsViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var getAllLeaguesUseCase: GetAllLeaguesUseCase
    private lateinit var fakeLeaguesListRepository: FakeLeagueListRepository
    private lateinit var getTeamsUseCase: GetTeamsByLeagueUseCase
    private lateinit var fakeTeamsListRepository: FakeTeamsListRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        fakeTeamsListRepository = FakeTeamsListRepository()
        @SpyK
        getTeamsUseCase = GetTeamsByLeagueUseCase(fakeTeamsListRepository, Dispatchers.IO)
        fakeLeaguesListRepository = FakeLeagueListRepository()
        @SpyK
        getAllLeaguesUseCase = GetAllLeaguesUseCase(fakeLeaguesListRepository, Dispatchers.IO)
    }

    @Test
    fun `when searching teams is done then uistate should refresh teams with same data`() = runTest {
        val leagueStr = "league1"
        val teamsViewModel = TeamsViewModel(getAllLeaguesUseCase, getTeamsUseCase)
        teamsViewModel.getTeams(leagueStr)
        dispatcher.scheduler.advanceUntilIdle()
        val fakeTeams = fakeTeamsListRepository.searchTeamsByLeague(leagueStr)

        fakeTeams.collect {
            val uiState = teamsViewModel.uiState.value
            assertEquals(false, uiState.isLoading)
            assertEquals(false, uiState.isError)
            assertEquals(teamsViewModel.keepOneOfTwoData(it.data!!), uiState.teams)
        }
    }

    @Test
    fun `when passing a list to keepOneOfTwo it keeps only one of two elements of the list`() = runTest {
        val teamsViewModel = TeamsViewModel(getAllLeaguesUseCase, getTeamsUseCase)
        val team1 = Team("1", "4334", "Team 1", "fakeUrl")
        val team2 = Team("2", "4334", "Team 2", "fakeUrl")
        val team3 = Team("3", "4334", "Team 3", "fakeUrl")
        val team4 = Team("4", "4334", "Team 4", "fakeUrl")
        val team5 = Team("5", "4334", "Team 5", "fakeUrl")
        val inputList = listOf<Team>(team1, team2, team3, team4, team5)
        val outputList = listOf<Team>(team5, team3, team1)

        assertEquals(teamsViewModel.keepOneOfTwoData(inputList), outputList)
    }

    @Test
    fun `when passing a list to keepOneOfTwo it should return it in an anti-alphabetic order`() = runTest {
        val teamsViewModel = TeamsViewModel(getAllLeaguesUseCase, getTeamsUseCase)
        val team1 = Team("1", "4334", "aaa", "fakeUrl")
        val team2 = Team("2", "4334", "bbb", "fakeUrl")
        val team3 = Team("3", "4334", "ccc", "fakeUrl")
        val team4 = Team("4", "4334", "ddd", "fakeUrl")
        val team5 = Team("5", "4334", "eee", "fakeUrl")
        val inputList = listOf(team1, team2, team3, team4, team5)
        val outputList = listOf(team5, team3, team1)

        assertEquals(teamsViewModel.keepOneOfTwoData(inputList), outputList)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
