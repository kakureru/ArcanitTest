package com.example.arcanittest.app.presentation.screens.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.arcanittest.app.presentation.collectFlowSafely
import com.example.arcanittest.databinding.FragmentFileBinding
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FileFragment : Fragment() {

    private val path: String? by lazy { requireArguments().getString(ARG_PATH) }
    private val repoId: Long by lazy {
        requireArguments().getLong(ARG_REPO_ID, -1L).takeIf { it > 0 } ?: throw IllegalArgumentException()
    }

    private lateinit var binding: FragmentFileBinding
    private val vm: FileViewModel by viewModel { parametersOf(repoId, path) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.errorLayout.btnTryAgain.setOnClickListener { vm.onTryAgainClick() }
        vm.uiState.render()
    }

    private fun Flow<FileUiState>.render() = collectFlowSafely {
        collect { state ->
            with(binding) {
                loader.isVisible = state.isLoading
                state.url?.let { webView.loadUrl(it) }
                webView.isVisible = state.error == null
                errorLayout.root.isVisible = state.error != null
                state.error?.let { errorLayout.message.setText(it) }
            }
        }
    }

    companion object {
        private const val ARG_PATH = "ARG_PATH"
        private const val ARG_REPO_ID = "ARG_REPO_ID"

        fun newInstance(repoId: Long, path: String) = FileFragment().apply {
            arguments = bundleOf(
                ARG_PATH to path,
                ARG_REPO_ID to repoId,
            )
        }
    }
}