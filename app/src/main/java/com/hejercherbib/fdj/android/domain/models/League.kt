package com.hejercherbib.fdj.android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Todo add Serlization to change name attribute
@Entity
data class League(
    @PrimaryKey
    val idLeague: String,
    val strLeague: String,
    val strSport: String,
    val strLeagueAlternate: String?
)
