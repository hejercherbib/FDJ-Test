package com.hejercherbib.fdj.android.data.dataSources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hejercherbib.fdj.android.domain.models.League

@Entity
data class LeagueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val sport: String,
    val alternate: String?
)

fun LeagueEntity.mapToModel() =
    League(
        id = this.id,
        name = this.name,
        alternate = this.alternate,
        sport = this.sport
    )
