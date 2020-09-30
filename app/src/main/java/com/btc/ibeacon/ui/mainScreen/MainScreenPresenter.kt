package com.btc.ibeacon.ui.mainScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.btc.ibeacon.R
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainScreenPresenter : MvpPresenter<MainScreenView>() {
    fun loadFragment(supportFragmentManager: FragmentManager, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            )
            .commit()
    }
}