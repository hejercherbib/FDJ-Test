package com.hejercherbib.fdj.android.data.network

import com.hejercherbib.fdj.android.data.models.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface TeamService {

    @GET("/api/{api_version}/json/{api_key}/search_all_teams.php")
    suspend fun searchAllTeams(@Query("l") leagueName: String): Response<TeamsResponse>

}