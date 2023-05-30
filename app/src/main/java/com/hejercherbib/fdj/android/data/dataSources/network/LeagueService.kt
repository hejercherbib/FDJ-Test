package com.hejercherbib.fdj.android.data.dataSources.network

import com.hejercherbib.fdj.android.data.dataSources.network.apiModels.LeaguesResponse
import retrofit2.Response
import retrofit2.http.GET

interface LeagueService {
    @GET("/api/{api_version}/json/{api_key}/all_leagues.php")
    suspend fun getAllLeagues(): Response<LeaguesResponse>
}
