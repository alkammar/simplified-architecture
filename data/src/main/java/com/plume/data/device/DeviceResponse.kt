package com.plume.data.device

sealed class RemoveDeviceResponse {
    object RemoveMainDeviceError: RemoveDeviceResponse()
    data class RemoveDeviceSuccess(
        val devices: List<DeviceItem>
    ): RemoveDeviceResponse()
}

sealed class DevicesResponse {
    object DevicesError: DevicesResponse()
    data class DevicesSuccess(
        val devices: List<DeviceItem>
    ): DevicesResponse()
}

data class DeviceItem(
    val macAddress: String,
    val name: String,
    val nodeId: String
)
