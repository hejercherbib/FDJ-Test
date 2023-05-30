package com.hejercherbib.fdj.android.data.dataSources.network.apiModels

import com.hejercherbib.fdj.android.data.dataSources.local.entities.LeagueEntity
import com.hejercherbib.fdj.android.domain.models.League

data class LeagueResponse(
    val idLeague: String,
    val strLeague: String,
    val strSport: String,
    val strLeagueAlternate: String?
)

fun LeagueResponse.mapToEntity() =
    LeagueEntity(
        id = this.idLeague,
        name = this.strLeague,
        alternate = this.strLeagueAlternate,
        sport = this.strSport
    )

fun LeagueResponse.mapToModel() =
    League(
        id = this.idLeague,
        name = this.strLeague,
        alternate = this.strLeagueAlternate,
        sport = this.strSport
    )
