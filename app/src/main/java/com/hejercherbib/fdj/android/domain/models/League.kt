package com.hejercherbib.fdj.android.domain.models

import androidx.room.PrimaryKey

data class League(
    @PrimaryKey
    val id: String,
    val name: String,
    val sport: String,
    val alternate: String?
)
