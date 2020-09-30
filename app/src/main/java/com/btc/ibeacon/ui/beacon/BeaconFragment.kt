package com.btc.ibeacon.ui.beacon

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.RemoteException
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon
import com.btc.ibeacon.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.frag_list.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class BeaconFragment : MvpAppCompatFragment(R.layout.frag_list), BeaconConsumer,
    BeaconAdapter.OnItemClickListener, BeaconView {
    private var beaconManager: BeaconManager? = null
    private lateinit var adapter: BeaconAdapter

    companion object {
        const val ITEM_KEY = "ITEM"
    }


    @InjectPresenter
    lateinit var presenter: BeaconPresenter

    @ProvidePresenter
    fun providePresenter(): BeaconPresenter {
        return BeaconPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beaconManager = activity?.applicationContext?.let {
            BeaconManager.getInstanceForApplication(
                it
            )
        }
        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        beaconManager?.bind(this)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BeaconAdapter(this)
        recyclerView.adapter = adapter
    }

    override fun getApplicationContext(): Context? {
        return activity?.applicationContext
    }

    override fun unbindService(p0: ServiceConnection?) {
        p0?.let { activity?.applicationContext?.unbindService(it) }
    }

    override fun bindService(p0: Intent?, p1: ServiceConnection, p2: Int): Boolean {
        return activity?.applicationContext?.bindService(p0, p1, p2)!!
    }

    override fun onBeaconServiceConnect() {
        beaconManager?.removeAllRangeNotifiers()
        beaconManager?.removeAllMonitorNotifiers()
        beaconManager?.addRangeNotifier { beacons, _ ->
            val result = ArrayList<Beacon>()
            beacons.forEach {
                val mac = it.bluetoothAddress
                val distance = it.distance
                val rssi = it.rssi
                val rangedUUID: String = it.id1.toString()
                val rangedMajor: String = it.id2.toString()
                val rangedMinor: String = it.id3.toString()
                val beacon = Beacon(mac, distance, rssi, rangedUUID, rangedMajor, rangedMinor)
                result.add(beacon)
            }
            result.sortBy { it.distance }
            presenter.showListOfBeacons(result)
        }
        try {
            beaconManager?.startRangingBeaconsInRegion(
                Region(
                    "myRangingUniqueId",
                    null,
                    null,
                    null
                )
            )
        } catch (e: RemoteException) {
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager?.unbind(this)

    }

    override fun showListOfBeacons(list: Collection<Beacon>) {
        adapter.addData(list as List<Beacon>)
    }

    override fun showInfo(item: Beacon) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(ITEM_KEY, item)
        startActivity(intent)
    }
}