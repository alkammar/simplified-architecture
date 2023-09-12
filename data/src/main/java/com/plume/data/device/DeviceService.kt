package com.plume.data.device

import com.plume.entity.exception.RemoveMainDeviceException
import kotlinx.coroutines.delay

class DeviceService {
    private var devices = listOf(
        DeviceItem("AA:BB:CC:DD:EE:01", "One Plus 10", "73"),
        DeviceItem("AA:BB:CC:DD:EE:02", "iPhone 15", "73"),
        DeviceItem("AA:BB:CC:DD:EE:03", "Macbook", "73")
    )

    suspend fun devices(): DevicesResponse {
        delay(3000)
        return DevicesResponse.DevicesSuccess(devices)
    }

    suspend fun remove(macAddress: String): RemoveDeviceResponse {
        delay(1000)
        if (macAddress == "AA:BB:CC:DD:EE:01") {
            throw RemoveMainDeviceException
        }
        val oldDevices = devices
        val newDevices = oldDevices.filterNot { device -> device.macAddress == macAddress }
        devices = newDevices
        return RemoveDeviceResponse.RemoveDeviceSuccess(devices)
    }
}