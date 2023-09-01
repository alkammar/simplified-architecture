package com.plume.domain.devicedetails

import com.plume.entity.Device
import com.plume.entity.Node

sealed class DeviceDetails {

    object Unknown: DeviceDetails()

    object NoDevice: DeviceDetails()

    data class NoNode(
        val device: Device
    ): DeviceDetails()

    data class HasDevice(
        val device: Device,
        val node: Node
    ): DeviceDetails()
}
