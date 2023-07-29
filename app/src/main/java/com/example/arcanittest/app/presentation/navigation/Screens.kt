package com.example.arcanittest.app.presentation.navigation

import com.example.arcanittest.app.presentation.screens.content.ContentFragment
import com.example.arcanittest.app.presentation.screens.file.FileFragment
import com.example.arcanittest.app.presentation.screens.search.SearchFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Search() = FragmentScreen {
        SearchFragment()
    }

    fun Content(repoId: Long) = FragmentScreen {
        ContentFragment.newInstance(repoId)
    }

    fun Content(repoId: Long, path: String) = FragmentScreen {
        ContentFragment.newInstance(repoId, path)
    }

    fun File(repoId: Long, path: String) = FragmentScreen {
        FileFragment.newInstance(repoId, path)
    }
}