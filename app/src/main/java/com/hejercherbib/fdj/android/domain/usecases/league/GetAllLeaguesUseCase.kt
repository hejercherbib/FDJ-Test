package com.hejercherbib.fdj.android.domain.usecases.league

import com.hejercherbib.fdj.android.di.IoDispatcher
import com.hejercherbib.fdj.android.domain.repositories.LeagueRepository
import com.hejercherbib.fdj.android.model.League
import com.hejercherbib.fdj.android.utils.FlowUseCase
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
    Use case responsible for fetching all leagues
    simple case but maybe more useful for more complicated processings or many data sources
 */
class GetAllLeaguesUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<League>>(dispatcher) {
    override suspend fun execute(parameters: Unit): Flow<Result<List<League>>> {
        return leagueRepository.getAllLeagues()
    }
}
