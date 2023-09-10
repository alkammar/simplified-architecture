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
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DeviceDetailsModule {

    @Provides
    fun providesGetDeviceDetailsUseCase(
        deviceRepository: DeviceRepository,
        nodeRepository: NodeRepository,
        @Named("io") ioCoroutineScope: CoroutineScope
    ) = GetDeviceDetailsUseCase(deviceRepository, nodeRepository, ioCoroutineScope)

    @Provides
    fun providesRemoveDeviceUseCase(
        deviceRepository: DeviceRepository,
        @Named("io") mainCoroutineScope: CoroutineScope,
        @Named("main") ioCoroutineScope: CoroutineScope
    ) = RemoveDeviceUseCase(deviceRepository, mainCoroutineScope, ioCoroutineScope)
}