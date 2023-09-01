package com.plume.domain.devicelist

import com.plume.domain.infra.StateUseCase
import com.plume.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class GetDeviceListUseCase(
    private val deviceRepository: DeviceRepository,
    coroutineScope: CoroutineScope
) : StateUseCase<Unit, DeviceList>(coroutineScope) {

    override suspend fun onExecute(request: Unit) =
        deviceRepository.flow()
            .map { devices ->
                if (devices.isEmpty()) {
                    DeviceList.NoDevices
                } else {
                    DeviceList.HasDevices(devices)
                }
            }
}