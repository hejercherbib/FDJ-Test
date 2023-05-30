package com.hejercherbib.fdj.android.data.repositories

import com.hejercherbib.fdj.android.data.dataSources.network.TeamService
import com.hejercherbib.fdj.android.di.IoDispatcher
import com.hejercherbib.fdj.android.utils.Result
import com.hejercherbib.fdj.android.model.Team
import com.hejercherbib.fdj.android.model.mapToModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface TeamRepository {
    suspend fun searchTeamsByLeague(leagueName: String): Flow<Result<List<Team>>>
}

@Singleton
class TeamRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteService: TeamService
) : TeamRepository {

    override suspend fun searchTeamsByLeague(leagueName: String): Flow<Result<List<Team>>> {
        return flow {
            emit(Result.Loading(true))

            val response = remoteService.searchAllTeams(leagueName)
            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.teams?.map { return@map it.mapToModel() }))
            } else {
                val errorMessage = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(Result.Error(errorMessage))
            }
        }.flowOn(ioDispatcher).catch {
            emit(Result.Error(it.localizedMessage))
        }
    }
}
