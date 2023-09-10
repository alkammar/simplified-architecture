package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.entity.Device
import com.plume.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class DeviceDataRepository(scope: CoroutineScope) :
    DeviceRepository,
    DataRepository<List<Device>>(scope) {

    override val emptyState = emptyList<Device>()
    override suspend fun persistedState() = listOf(
        Device("AA:BB:CC:DD:EE:01", "One Plus 10", "73")
    )

    override suspend fun remoteState(): List<Device> {
        delay(3000)
        return listOf(
            Device("AA:BB:CC:DD:EE:01", "One Plus 10", "73"),
            Device("AA:BB:CC:DD:EE:02", "iPhone 15", "73"),
            Device("AA:BB:CC:DD:EE:03", "Macbook", "73")
        )
    }

    override suspend fun removeDevice(macAddress: String) {
        println("removeDevice $macAddress")
        throw IllegalArgumentException()
        val oldDevices = latest()
        val newDevices = oldDevices.filterNot { device -> device.macAddress == macAddress }
        emit(newDevices)
    }
}