package com.plume.domain.devicedetails

import com.plume.domain.infra.ActionUseCase
import com.plume.repository.DeviceRepository

class RemoveDeviceUseCase(
    private val deviceRepository: DeviceRepository
) : ActionUseCase<String> {

    override suspend fun onExecute(request: String) {
        deviceRepository.removeDevice(request)
    }
}