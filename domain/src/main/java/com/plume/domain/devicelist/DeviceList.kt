package com.plume.domain.devicelist

import com.plume.entity.Device

sealed class DeviceList {

    object Unknown: DeviceList()

    object NoDevices: DeviceList()

    data class HasDevices(
        val devices: List<Device>
    ): DeviceList()
}
