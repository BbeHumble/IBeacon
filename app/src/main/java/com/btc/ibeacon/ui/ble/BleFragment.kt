package com.btc.ibeacon.ui.ble

import android.os.Bundle
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.frag_list.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class BleFragment : MvpAppCompatFragment(R.layout.frag_list), BleView {
    private lateinit var adapter: BleAdapter
    private var scanSubscription: Disposable? = null

    companion object {
        const val DATAKEY = "DATA_KEY"
    }

    @InjectPresenter
    lateinit var presenter: BlePresenter

    @ProvidePresenter
    fun providePresenter(): BlePresenter {
        return BlePresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val data = adapter.getData()
        val gson = Gson()
        outState.putString(DATAKEY, gson.toJson(data))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val gson = Gson()
        val itemType = object : TypeToken<ArrayList<Beacon>>() {}.type
        if (savedInstanceState?.getString(DATAKEY) != null)
            adapter.addAllData(gson.fromJson(savedInstanceState.getString(DATAKEY), itemType))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BleAdapter()
        recyclerView.adapter = adapter
        val rxBleClient = context?.let { RxBleClient.create(it) }
        scanSubscription = rxBleClient?.scanBleDevices(
            ScanSettings.Builder()
                .build()
        )
            ?.subscribe(
                {
                    presenter.addData(
                        adapter,
                        Beacon(
                            it.bleDevice.macAddress.toString(),
                            0.0,
                            it.rssi,
                            "",
                            "",
                            ""
                        )
                    )
                },
                {
                    it.printStackTrace()
                }
            )


    }

}