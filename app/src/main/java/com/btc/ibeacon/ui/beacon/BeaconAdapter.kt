package com.btc.ibeacon.ui.beacon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon

class BeaconAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<BeaconHolder>() {
    private val eventItems: MutableList<Beacon> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeaconHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.li_beacon, parent, false)
        return BeaconHolder(view)
    }

    override fun onBindViewHolder(holder: BeaconHolder, position: Int) {
        val eventItem = eventItems[position]
        holder.bind(eventItem, onItemClickListener, holder.itemView)

    }

    fun getData(): ArrayList<Beacon> {
        return eventItems as ArrayList<Beacon>
    }

    override fun getItemCount(): Int {
        return eventItems.size
    }

    fun addAllData(list: List<Beacon>) {
        eventItems.clear()
        eventItems.addAll(list)
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun showInfo(item: Beacon)
    }
}