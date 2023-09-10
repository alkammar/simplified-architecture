package com.plume.domain.devicedetails

import com.plume.domain.infra.ActionUseCase
import com.plume.repository.DeviceRepository
import kotlinx.coroutines.CoroutineScope

class RemoveDeviceUseCase(
    private val deviceRepository: DeviceRepository,
    mainCoroutineScope: CoroutineScope,
    ioCoroutineScope: CoroutineScope
) : ActionUseCase<String>(mainCoroutineScope, ioCoroutineScope) {

    override suspend fun onExecute(request: String) {
        println("onExecute $request")
        deviceRepository.removeDevice(request)
    }
}