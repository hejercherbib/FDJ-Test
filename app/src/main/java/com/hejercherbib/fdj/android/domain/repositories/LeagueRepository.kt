package com.hejercherbib.fdj.android.domain.repositories

import com.hejercherbib.fdj.android.data.database.LeagueDao
import com.hejercherbib.fdj.android.data.network.LeagueService
import com.hejercherbib.fdj.android.model.League
import com.hejercherbib.fdj.android.utils.Result
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

interface LeagueRepository {
    suspend fun getAllLeagues(): Flow<Result<List<League>>>
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
            val result = dao.getAll()
            if (result.isNotEmpty()) {
                emit(Result.Success(dao.getAll()))
                return@flow
            }

            emit(Result.Loading(true))

            val response = remoteService.getAllLeagues()
            if (response.isSuccessful) {
                response.body()?.leagues?.let {
                    dao.insertAll(it)
                }
                emit(Result.Success(response.body()?.leagues))
            } else {
                val errorMessage = response.errorBody()?.string()
                response.errorBody()?.close()
                flowOf(Result.Error(errorMessage))
            }
        }
    } catch (e: Exception) {
        Timber.d("Error getting Leagues list")
        flowOf(Result.Error(e.localizedMessage))
    }
}
