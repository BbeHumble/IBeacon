package com.btc.ibeacon.ui.ble

import com.btc.ibeacon.model.Beacon
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class BlePresenter : MvpPresenter<BleView>() {
    fun addData(adapter: BleAdapter, ble: Beacon) {
        adapter.addData(ble)
    }
}