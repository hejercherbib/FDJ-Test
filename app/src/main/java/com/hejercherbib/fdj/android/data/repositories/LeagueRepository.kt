package com.hejercherbib.fdj.android.data.repositories

import com.hejercherbib.fdj.android.data.dataSources.local.LeagueDao
import com.hejercherbib.fdj.android.data.dataSources.local.entities.mapToModel
import com.hejercherbib.fdj.android.data.dataSources.network.LeagueService
import com.hejercherbib.fdj.android.data.dataSources.network.apiModels.mapToEntity
import com.hejercherbib.fdj.android.data.dataSources.network.apiModels.mapToModel
import com.hejercherbib.fdj.android.domain.models.League
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

interface LeagueRepository {
    suspend fun getAllLeagues(): Flow<Result<List<League>>>
    suspend fun fetchLeaguesFromRemote(): Flow<Result<List<League>>>
}

class LeagueRepositoryImpl @Inject constructor(
    private val remoteService: LeagueService,
    private val dao: LeagueDao
) : LeagueRepository {

    /*
    get All leagues from db (if data exists) else fetch them from remote source
     */
    override suspend fun getAllLeagues(): Flow<Result<List<League>>> = try {
        flow {
            // start by getting data directly from database because data should not change frequently
            // a background job should be scheduled each x(HOUR) to fetch directly from remote
            val result = dao.getAll()
            if (result.isNotEmpty()) {
                emit(Result.Success(dao.getAll().map { return@map it.mapToModel() }))
                return@flow
            }
            emit(Result.Loading(true))

            val response = remoteService.getAllLeagues()
            if (response.isSuccessful) {
                response.body()?.leagues?.let { leagueResponse ->
                    dao.insertAll(leagueResponse.map { return@map it.mapToEntity() })
                    emit(
                        Result.Success(
                            leagueResponse.map { return@map it.mapToModel() }
                        )
                    )
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                response.errorBody()?.close()
                flowOf(Result.Error(errorMessage))
            }
        }
    } catch (e: Exception) {
        Timber.d("getAllLeagues error")
        flowOf(Result.Error(e.localizedMessage))
    }

    /*
    method to fetch league list data only from remote
    to be called in scheduled job
     */
    override suspend fun fetchLeaguesFromRemote(): Flow<Result<List<League>>> = try {
        flow {
            emit(Result.Loading(true))
            val response = remoteService.getAllLeagues()
            if (response.isSuccessful) {
                response.body()?.leagues?.let { leagueResponse ->
                    dao.insertAll(leagueResponse.map { return@map it.mapToEntity() })
                    emit(
                        Result.Success(
                            leagueResponse.map { return@map it.mapToModel() }
                        )
                    )
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                response.errorBody()?.close()
                flowOf(Result.Error(errorMessage))
            }
        }
    } catch (e: Exception) {
        Timber.d("fetchLeaguesFromRemote error")
        flowOf(Result.Error(e.localizedMessage))
    }
}
