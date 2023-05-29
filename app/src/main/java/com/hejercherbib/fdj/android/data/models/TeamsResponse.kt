package com.hejercherbib.fdj.android.data.models

import com.hejercherbib.fdj.android.model.Team
import kotlinx.serialization.Serializable

data class TeamsResponse(
    val teams: List<Team>
)
