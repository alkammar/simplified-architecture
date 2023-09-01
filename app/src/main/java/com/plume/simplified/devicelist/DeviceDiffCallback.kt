package com.plume.simplified.devicelist

import androidx.recyclerview.widget.DiffUtil
import com.plume.entity.Device

class DeviceDiffCallback(
    private val oldDevices: List<Device>,
    private val newDevices: List<Device>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldDevices.size
    }

    override fun getNewListSize(): Int {
        return newDevices.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDevices[oldItemPosition].macAddress == newDevices[newItemPosition].macAddress
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDevice = oldDevices[oldItemPosition]
        val newDevice = newDevices[newItemPosition]
        return oldDevice.name == newDevice.name
    }
}