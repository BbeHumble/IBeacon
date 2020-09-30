package com.btc.ibeacon.ui.ble

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon


class BleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mac: TextView = itemView.findViewById(R.id.liBleMac)
    private val rssi: TextView = itemView.findViewById(R.id.liRSSI)

    fun bind(
        item: Beacon,
        view: View
    ) {
        mac.text = item.mac
        rssi.text = item.rssi.toString()
    }

}