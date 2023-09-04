package com.plume.simplified.devicelist

import com.plume.domain.devicelist.GetDeviceListUseCase
import com.plume.repository.DeviceRepository
import com.plume.simplified.application.Scope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DeviceListModule {

    @Provides
    fun providesGetDeviceListUseCase(
        deviceRepository: DeviceRepository,
        scope: Scope
    ) = GetDeviceListUseCase(deviceRepository, scope.mainCoroutineScope)
}