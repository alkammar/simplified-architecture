package com.plume.simplified.devicelist

import com.plume.domain.devicelist.GetDeviceListUseCase
import com.plume.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DeviceListModule {

    @Provides
    fun providesGetDeviceListUseCase(
        deviceRepository: DeviceRepository,
        @Named("main") mainCoroutineScope: CoroutineScope,
        @Named("io") ioCoroutineScope: CoroutineScope
    ) = GetDeviceListUseCase(deviceRepository, mainCoroutineScope, ioCoroutineScope)
}