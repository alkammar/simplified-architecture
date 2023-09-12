package com.plume.data

import com.plume.data.device.DeviceItem
import com.plume.data.device.DeviceService
import com.plume.data.device.DevicesResponse
import com.plume.data.device.RemoveDeviceResponse
import com.plume.data.infra.DataRepository
import com.plume.entity.Device
import com.plume.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import java.lang.IllegalArgumentException

class DeviceDataRepository(private val deviceService: DeviceService, scope: CoroutineScope) :
    DeviceRepository,
    DataRepository<List<Device>>(scope) {

    override val emptyState = emptyList<Device>()
    override suspend fun persistedState() = listOf(
        Device("AA:BB:CC:DD:EE:01", "One Plus 10", "73")
    )

    override suspend fun remoteState() =
        when (val response = deviceService.devices()) {
            DevicesResponse.DevicesError -> throw IllegalArgumentException()
            is DevicesResponse.DevicesSuccess -> response.devices.map { it.toData() }
        }

    override suspend fun removeDevice(macAddress: String) {
        emit(
            when (val response = deviceService.remove(macAddress)) {
                RemoveDeviceResponse.RemoveMainDeviceError -> throw IllegalArgumentException()
                is RemoveDeviceResponse.RemoveDeviceSuccess -> response.devices.map { it.toData() }
            }
        )
    }

    private fun DeviceItem.toData(): Device = Device(macAddress, name, nodeId)
}