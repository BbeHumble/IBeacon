package com.btc.ibeacon.ui.details

import android.os.Bundle
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon
import com.btc.ibeacon.ui.beacon.BeaconFragment
import kotlinx.android.synthetic.main.activity_details.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class DetailsActivity : MvpAppCompatActivity(R.layout.activity_details), DetailsView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val beacon: Beacon? = intent.extras?.getSerializable(BeaconFragment.ITEM_KEY) as Beacon?
        setFields(beacon)
    }

    @InjectPresenter
    lateinit var presenter: DetailsPresenter

    @ProvidePresenter
    fun providePresenter(): DetailsPresenter {
        return DetailsPresenter()
    }


    private fun setFields(beacon: Beacon?) {
        rssiDetails.text = beacon?.rssi.toString()
        UUIDDetail.text = beacon?.UUID
        majorDetail.text = beacon?.major
        minorDetail.text = beacon?.minor
        distanceDetail.text = beacon?.distance.toString()
    }

}