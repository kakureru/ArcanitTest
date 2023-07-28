package com.example.arcanittest.app.presentation.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.arcanittest.app.presentation.adapter.DelegateListAdapter
import com.example.arcanittest.app.presentation.collectFlowSafely
import com.example.arcanittest.app.presentation.screens.search.adapter.RepoDelegate
import com.example.arcanittest.app.presentation.screens.search.adapter.UserDelegate
import com.example.arcanittest.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val vm: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DelegateListAdapter().apply {
            addDelegate(UserDelegate())
            addDelegate(RepoDelegate())
        }
        binding.list.adapter = adapter
        collectFlowSafely {
            vm.searchResult.collect {
                adapter.submitList(it)
            }
        }
        binding.searchField.doOnTextChanged { text, _, _, _ ->
            vm.onSearchTextChanged(text.toString())
        }
    }
}