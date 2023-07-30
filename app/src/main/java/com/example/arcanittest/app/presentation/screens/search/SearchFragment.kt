package com.example.arcanittest.app.presentation.screens.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.adapter.DelegateListAdapter
import com.example.arcanittest.app.presentation.collectFlowSafely
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoCallback
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoDelegate
import com.example.arcanittest.app.presentation.screens.search.adapter.UserCallback
import com.example.arcanittest.app.presentation.screens.search.adapter.UserDelegate
import com.example.arcanittest.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val vm: SearchViewModel by viewModel()

    private val repoCallback = object : RepoCallback {
        override fun onClick(repoId: Long) = vm.onRepoClick(repoId)
    }

    private val userCallback = object : UserCallback {
        override fun onClick(userId: Long) = vm.onUserClick(userId)
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
            errorLayout.btnTryAgain.setOnClickListener { vm.onTryAgainClick() }
        }
        vm.uiState.render()
        vm.effect.handleEffect()
    }

    private fun Flow<SearchUiState>.render() = collectFlowSafely {
        collect { state ->
            with(binding) {
                adapter.submitList(state.data)
                list.isVisible = state.error == null
                searchField.isEnabled = state.isSearchFieldEnabled
                btnSearch.isEnabled = state.isSearchButtonEnabled
                loader.isVisible = state.isLoading
                errorLayout.root.isVisible = state.error != null
                state.error?.let { errorLayout.message.setText(it.msg) }
            }
        }
    }

    private fun Flow<SearchEffect>.handleEffect() = collectFlowSafely {
        collect { effect ->
            when(effect) {
                is SearchEffect.OpenUserPage -> openUserPage(effect.url)
            }
        }
    }

    private fun openUserPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val title = resources.getString(R.string.hint_open_with)
        val chooser = Intent.createChooser(intent, title)
        try {
            requireContext().startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.error_app_not_found, Toast.LENGTH_SHORT).show()
        }
    }
}