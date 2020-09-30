package com.btc.ibeacon.ui.ble

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon

class BleAdapter :
    RecyclerView.Adapter<BleHolder>() {
    private val eventItems: MutableList<Beacon> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.li_ble, parent, false)
        return BleHolder(view)
    }

    override fun onBindViewHolder(holder: BleHolder, position: Int) {
        val eventItem = eventItems[position]
        holder.bind(eventItem, holder.itemView)

    }

    fun getData(): ArrayList<Beacon> {
        return eventItems as ArrayList<Beacon>
    }

    override fun getItemCount(): Int {
        return eventItems.size
    }

    fun addData(data: Beacon) {
        var containMac = false
        eventItems.forEach {
            if (it.mac == data.mac) {
                it.rssi = data.rssi
                containMac = true
            }
        }
        if (!containMac) {
            eventItems.add(data)
        }
        eventItems.sortBy { it.rssi }
        notifyDataSetChanged()
    }

    fun addAllData(list: List<Beacon>) {
        eventItems.clear()
        eventItems.addAll(list)
        notifyDataSetChanged()
    }

}