package com.example.arcanittest.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.arcanittest.R
import com.example.arcanittest.app.presentation.navigation.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()
    private val navigator = AppNavigator(this, R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            router.replaceScreen(Screens.Search())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        router.exit()
    }
}