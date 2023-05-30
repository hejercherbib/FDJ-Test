package com.hejercherbib.fdj.android.repositoriesTests

import com.hejercherbib.fdj.android.data.repositories.TeamRepository
import com.hejercherbib.fdj.android.model.Team
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTeamsListRepository : TeamRepository {

    val team1 = Team("1", "4334", "Team 1", "fakeUrl")
    val team2 = Team("2", "4334", "Team 2", "fakeUrl")
    val team3 = Team("3", "4334", "Team 3", "fakeUrl")
    val team4 = Team("4", "4334", "Team 4", "fakeUrl")
    val team5 = Team("5", "4334", "Team 5", "fakeUrl")

    val fakeTeams = listOf<Team>(team1, team2, team3, team4, team5)

    override suspend fun searchTeamsByLeague(leagueName: String): Flow<Result<List<Team>>> =
        flow { emit(Result.Success(fakeTeams)) }
}
