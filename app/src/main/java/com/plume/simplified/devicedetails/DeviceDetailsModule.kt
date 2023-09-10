package com.plume.simplified.devicedetails

import com.plume.domain.devicedetails.GetDeviceDetailsUseCase
import com.plume.domain.devicedetails.RemoveDeviceUseCase
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DeviceDetailsModule {

    @Provides
    fun providesGetDeviceDetailsUseCase(
        deviceRepository: DeviceRepository,
        nodeRepository: NodeRepository
    ) = GetDeviceDetailsUseCase(
        deviceRepository,
        nodeRepository
    )

    @Provides
    fun providesRemoveDeviceUseCase(
        deviceRepository: DeviceRepository
    ) = RemoveDeviceUseCase(deviceRepository)
}