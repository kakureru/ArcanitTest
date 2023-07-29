package com.example.arcanittest.app.presentation.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.arcanittest.app.presentation.collectFlowSafely
import com.example.arcanittest.app.presentation.screens.search.adapter.DelegateListAdapter
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoCallback
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoDelegate
import com.example.arcanittest.app.presentation.screens.search.adapter.UserCallback
import com.example.arcanittest.app.presentation.screens.search.adapter.UserDelegate
import com.example.arcanittest.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val vm: SearchViewModel by viewModel()

    private val repoCallback = object : RepoCallback {
        override fun onClick(repoId: Long) = vm.onRepoClick(repoId)
    }

    private val userCallback = object : UserCallback {
        override fun onClick(userId: Long) = Unit
    }

    private val adapter = DelegateListAdapter().apply {
        addDelegate(UserDelegate(userCallback))
        addDelegate(RepoDelegate(repoCallback))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            list.adapter = adapter
            searchField.addTextChangedListener { vm.onSearchTextChanged("$it") }
            btnSearch.setOnClickListener { vm.onSearchClick() }
        }
        vm.uiState.render()
    }

    private fun StateFlow<SearchUiState>.render() = collectFlowSafely {
        collect { state ->
            with(binding) {
                adapter.submitList(state.searchResult)
                searchField.isEnabled = state.isSearchFieldEnabled
                btnSearch.isEnabled = state.isSearchButtonEnabled
                loader.isVisible = state.isLoading
            }
        }
    }
}