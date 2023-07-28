package com.example.arcanittest.app.presentation.navigation

import com.example.arcanittest.app.presentation.screens.search.SearchFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Search() = FragmentScreen {
        SearchFragment()
    }
}