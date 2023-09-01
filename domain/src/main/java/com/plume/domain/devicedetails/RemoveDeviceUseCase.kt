package com.plume.domain.devicedetails

import com.plume.domain.infra.ActionUseCase
import com.plume.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope

class RemoveDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    coroutineScope: CoroutineScope
) : ActionUseCase<String>(coroutineScope) {

    override suspend fun onExecute(request: String) {
        deviceRepository.removeDevice(request)
    }
}