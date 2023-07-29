package com.example.arcanittest.app.presentation.screens.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.arcanittest.app.presentation.collectFlowSafely
import com.example.arcanittest.app.presentation.screens.content.adapter.ContentAdapter
import com.example.arcanittest.app.presentation.screens.content.adapter.ContentCallback
import com.example.arcanittest.databinding.FragmentContentBinding
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContentFragment : Fragment() {

    private val path: String? by lazy { requireArguments().getString(ARG_PATH) }
    private val repoId: Long by lazy {
        requireArguments().getLong(ARG_REPO_ID, -1L).takeIf { it > 0 } ?: throw IllegalArgumentException()
    }

    private lateinit var binding: FragmentContentBinding
    private val vm: ContentViewModel by viewModel { parametersOf(repoId, path) }

    private val contentCallback = object : ContentCallback {
        override fun onClick(item: ContentItem) = vm.onContentClick(item)
    }

    private val adapter = ContentAdapter(contentCallback)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            content.adapter = adapter
        }
        vm.uiState.render()
    }

    private fun Flow<ContentUiState>.render() = collectFlowSafely {
        collect { state ->
            with(binding) {
                path.text = state.path
                loader.isVisible = state.isLoading
            }
            adapter.submitList(state.content)
        }
    }

    companion object {
        private const val ARG_PATH = "ARG_PATH"
        private const val ARG_REPO_ID = "ARG_REPO_ID"
        fun newInstance(repoId: Long): Fragment = ContentFragment().apply {
            arguments = bundleOf(
                ARG_REPO_ID to repoId,
            )
        }

        fun newInstance(repoId: Long, path: String): Fragment = ContentFragment().apply {
            arguments = bundleOf(
                ARG_PATH to path,
                ARG_REPO_ID to repoId,
            )
        }
    }
}