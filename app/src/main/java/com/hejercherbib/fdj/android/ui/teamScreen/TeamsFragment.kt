package com.hejercherbib.fdj.android.ui.teamScreen

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hejercherbib.fdj.android.R
import com.hejercherbib.fdj.android.databinding.FragmentTeamsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsFragment : Fragment() {
    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamsViewModel by activityViewModels()
    private lateinit var suggestionsAdapter: SimpleCursorAdapter
    private lateinit var teamsAdapter: TeamsAdapter

    companion object {
        fun newInstance() = TeamsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // todo check if network available then fetch data else show network error msg and add listener for connectivity
        fillViews()
        setSearchQuery()
        observeUiStates()
    }

    private fun observeUiStates() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect() {
                    teamsAdapter.submitList(it.teams)
                    manageErrorMsg()
                }
            }
        }
    }

    private fun initRecyclerView() {
        teamsAdapter = TeamsAdapter()
        binding.rcvLeagues.adapter = teamsAdapter
    }

    private fun fillViews() {
        initRecyclerView()
        manageErrorMsg()
        setupSearchView()
    }

    private fun manageErrorMsg() {
        // todo dissociate different error cases
        binding.txvErrorMsg.visibility = viewModel.getErrorMsgVisibility()
        binding.txvErrorMsg.text = getString(R.string.teams_error_msg)
    }

    private fun setupSearchView() {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_txv_league_name)
        suggestionsAdapter = SimpleCursorAdapter(context, R.layout.item_suggestion_league, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        binding.searchView.suggestionsAdapter = suggestionsAdapter
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun setSearchQuery() {
        lifecycleScope.launch {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        viewModel.clearTeamsList()
                        return true
                    }

                    val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                    newText.let {
                        viewModel.uiState.value.leagues?.let {
                            it.forEachIndexed { index, suggestion ->
                                suggestion.strLeague.let {
                                    if (suggestion.strLeague.contains(newText, true)) {
                                        cursor.addRow(arrayOf(index, suggestion.strLeague))
                                    }
                                }
                            }
                        }
                        suggestionsAdapter.changeCursor(cursor)
                    }
                    return true
                }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })
            binding.searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor = binding.searchView.suggestionsAdapter.getItem(position) as Cursor
                    if (cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1) > 0) {
                        val selection =
                            cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                        binding.searchView.setQuery(selection, false)
                        viewModel.getTeams(selection)
                    }
                    return true
                }
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
