package com.btc.ibeacon.ui.beacon

import com.btc.ibeacon.model.Beacon
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BeaconView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showListOfBeacons(list: Collection<Beacon>)
}