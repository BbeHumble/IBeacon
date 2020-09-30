package com.btc.ibeacon.ui.beacon

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.btc.ibeacon.R
import com.btc.ibeacon.model.Beacon
import java.lang.String
import java.util.*


class BeaconHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mac: TextView = itemView.findViewById(R.id.limac)
    private val uuid: TextView = itemView.findViewById(R.id.liUUID)
    private val distance: TextView = itemView.findViewById(R.id.lidistance)

    fun bind(
        item: Beacon,
        onItemClickListener: BeaconAdapter.OnItemClickListener?,
        view: View
    ) {
        mac.text = item.mac
        uuid.text = item.UUID

        distance.text = String.format(Locale.ENGLISH, "%(.2f", item.distance)
        if (onItemClickListener != null) {
            itemView.setOnClickListener {
                onItemClickListener.showInfo(
                    item
                )
            }
        }
    }

}