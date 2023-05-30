package com.hejercherbib.fdj.android.model

data class TeamResponse(
    val idTeam: String,
    val idLeague: String,
    val strTeam: String,
    val strTeamBadge: String
)

fun TeamResponse.mapToModel() =
    Team(
        idTeam = this.idTeam,
        idLeague = this.idLeague,
        strTeam = this.strTeam,
        strTeamBadge = this.strTeamBadge
    )
