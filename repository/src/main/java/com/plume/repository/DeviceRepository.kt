package com.plume.repository

import com.plume.entity.Device
import infra.Repository

interface DeviceRepository: Repository<List<Device>> {
    suspend fun removeDevice(macAddress: String)
}