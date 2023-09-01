package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.entity.Device
import com.plume.repository.DeviceRepository

class DeviceDataRepository :
    DeviceRepository,
    DataRepository<List<Device>>() {

    override val initialState = listOf(
        Device("AA:BB:CC:DD:EE:01", "One Plus 10", "73"),
        Device("AA:BB:CC:DD:EE:02", "iPhone 15", "73"),
        Device("AA:BB:CC:DD:EE:03", "Macbook", "73")
    )

    override suspend fun removeDevice(macAddress: String) {
        val oldDevices = stateFlow.value
        val newDevices = oldDevices.filterNot { device -> device.macAddress == macAddress }
        stateFlow.emit(newDevices)
    }
}