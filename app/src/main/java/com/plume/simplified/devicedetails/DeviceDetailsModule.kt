package com.plume.simplified.devicedetails

import com.plume.domain.devicedetails.GetDeviceDetailsUseCase
import com.plume.domain.devicedetails.RemoveDeviceUseCase
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ViewModelComponent::class)
class DeviceDetailsModule {

    @Provides
    fun providesGetDeviceDetailsUseCase(
        deviceRepository: DeviceRepository,
        nodeRepository: NodeRepository,
        coroutineScope: CoroutineScope
    ) = GetDeviceDetailsUseCase(deviceRepository, nodeRepository, coroutineScope)

    @Provides
    fun providesRemoveDeviceUseCase(
        deviceRepository: DeviceRepository,
        coroutineScope: CoroutineScope
    ) = RemoveDeviceUseCase(deviceRepository, coroutineScope)
}