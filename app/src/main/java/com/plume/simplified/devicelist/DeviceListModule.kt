package com.plume.simplified.devicelist

import com.plume.domain.devicelist.GetDeviceListUseCase
import com.plume.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ViewModelComponent::class)
class DeviceListModule {

    @Provides
    fun providesGetDeviceListUseCase(
        deviceRepository: DeviceRepository,
        coroutineScope: CoroutineScope
    ) = GetDeviceListUseCase(deviceRepository, coroutineScope)
}