package com.plume.simplified.devicelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.plume.entity.Device
import com.plume.simplified.R

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    var data: List<Device> = emptyList()
        set(value) {
            val result = DiffUtil.calculateDiff(DeviceDiffCallback(field, value))
            field = value
            result.dispatchUpdatesTo(this)
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.device_item_label)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_device, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data[position].name
        viewHolder.itemView.setOnClickListener {
            val action = DeviceListFragmentDirections.actionDeviceListFragmentToDeviceDetailsFragment(data[position].macAddress)
            findNavController(viewHolder.itemView).navigate(action)
        }
    }

    override fun getItemCount() = data.size

}
