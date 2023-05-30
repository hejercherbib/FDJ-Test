package com.hejercherbib.fdj.android.domain.usecases.team

import com.hejercherbib.fdj.android.data.repositories.TeamRepository
import com.hejercherbib.fdj.android.di.IoDispatcher
import com.hejercherbib.fdj.android.model.Team
import com.hejercherbib.fdj.android.utils.FlowUseCase
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
    Use case responsible for searching a league's teams (via api)
    simple case but maybe more useful for more complicated processings or many data sources
 */
class GetTeamsByLeagueUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<String, List<Team>>(dispatcher) {
    override suspend fun execute(league: String): Flow<Result<List<Team>>> {
        return teamRepository.searchTeamsByLeague(league)
    }
}
