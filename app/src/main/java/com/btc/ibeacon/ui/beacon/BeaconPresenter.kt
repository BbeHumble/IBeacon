package com.btc.ibeacon.ui.beacon

import com.btc.ibeacon.model.Beacon
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class BeaconPresenter : MvpPresenter<BeaconView>() {
    fun showListOfBeacons(beacons: MutableCollection<Beacon>) {
        viewState.showListOfBeacons(beacons)
    }
}