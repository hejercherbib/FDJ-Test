package com.hejercherbib.fdj.android.ui.teamScreen

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hejercherbib.fdj.android.domain.usecases.league.GetAllLeaguesUseCase
import com.hejercherbib.fdj.android.domain.usecases.team.GetTeamsByLeagueUseCase
import com.hejercherbib.fdj.android.model.League
import com.hejercherbib.fdj.android.model.Team
import com.hejercherbib.fdj.android.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getAllLeaguesUseCase: GetAllLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Todo remote call in splashscreen and in a background job
        getAllLeagues()
    }

    /*
     To clear teams list after clearing search
     */
    fun clearTeamsList() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    teams = emptyList()
                )
            }
        }
    }

    fun getErrorMsgVisibility(): Int {
        return if (uiState.value.isError) View.VISIBLE
        else View.GONE
    }

    fun getAllLeagues() {
        viewModelScope.launch {
            getAllLeaguesUseCase(Unit).collect { result ->
                when (result) {
                    is Result.Success -> _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            leagues = result.data
                        )
                    }
                    is Result.Loading -> _uiState.update { currentState ->
                        currentState.copy(isLoading = true)
                    }
                    is Result.Error -> _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    /*
      get selected league's teams
     */
    fun getTeams(leagueName: String) {
        viewModelScope.launch {
            getTeamsByLeagueUseCase(leagueName).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { state ->
                        state.copy(isLoading = true)
                    }

                    is Result.Success -> when {
                        result.data.isNullOrEmpty() -> _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                teams = emptyList(),
                                isError = false
                            )
                        }

                        else -> {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    teams = keepOneOfTwoData(result.data) ,
                                    isError = false

                                )
                            }
                        }
                    }

                    is Result.Error -> _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            teams = emptyList(),
                            isError = true
                        )
                    }
                }
            }
        }
    }

    /*
    keep only one of two returned teams
    and
    return it in an anti-alphabetic order
     */
    fun keepOneOfTwoData(data: List<Team>): List<Team> {
        return data.slice(data.indices step 2)
            .sortedByDescending { it.strTeam }
    }

    data class UiState(
        var teams: List<Team> = emptyList(),
        var leagues: List<League>? = emptyList(),
        var isLoading: Boolean = false,
        var isError: Boolean = false
    )
}
