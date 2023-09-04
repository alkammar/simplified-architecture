package com.plume.simplified.devicedetails

import com.plume.domain.devicedetails.GetDeviceDetailsUseCase
import com.plume.domain.devicedetails.RemoveDeviceUseCase
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import com.plume.simplified.application.Scope
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
        nodeRepository: NodeRepository,
        scope: Scope
    ) = GetDeviceDetailsUseCase(deviceRepository, nodeRepository, scope.mainCoroutineScope)

    @Provides
    fun providesRemoveDeviceUseCase(
        deviceRepository: DeviceRepository,
        scope: Scope
    ) = RemoveDeviceUseCase(deviceRepository, scope.mainCoroutineScope)
}