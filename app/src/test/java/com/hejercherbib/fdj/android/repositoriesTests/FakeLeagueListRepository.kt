package com.hejercherbib.fdj.android.repositoriesTests

import com.hejercherbib.fdj.android.domain.repositories.LeagueRepository
import com.hejercherbib.fdj.android.model.League
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLeagueListRepository : LeagueRepository {
    val league1 = League("21", "League1", "Foot", "fake")
    val league2 = League("22", "League2", "Foot", "fake")
    val league3 = League("23", "League3", "Foot", "fake")
    val league4 = League("24", "League4", "Foot", "fake")
    val fakeLeagues = listOf(league1, league2, league3, league4)

    override suspend fun getAllLeagues(): Flow<Result<List<League>>> =
        flow { emit(Result.Success(fakeLeagues)) }
}
