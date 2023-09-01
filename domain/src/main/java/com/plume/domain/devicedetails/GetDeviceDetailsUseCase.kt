package com.plume.domain.devicedetails

import com.plume.domain.infra.StateUseCase
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine

class GetDeviceDetailsUseCase(
    private val deviceRepository: DeviceRepository,
    private val nodeRepository: NodeRepository,
    coroutineScope: CoroutineScope
) : StateUseCase<String, DeviceDetails>(coroutineScope) {

    override suspend fun onExecute(request: String) = combine(
        deviceRepository.flow(),
        nodeRepository.flow()
    ) { devices, nodes ->
        val device = devices.firstOrNull { device -> device.macAddress == request }
        if (device == null) {
            DeviceDetails.NoDevice
        } else {
            val node = nodes.firstOrNull { node -> node.id == device.nodeId }
            if (node == null) {
                DeviceDetails.NoNode(device)
            } else {
                DeviceDetails.HasDevice(device, node)
            }
        }
    }
}