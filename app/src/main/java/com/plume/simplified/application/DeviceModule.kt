package com.plume.simplified.application

import com.plume.data.DeviceDataRepository
import com.plume.data.device.DeviceService
import com.plume.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {
    @Provides
    @Singleton
    fun providesDeviceService() = DeviceService()

    @Provides
    @Singleton
    fun providesDeviceRepository(
        deviceService: DeviceService,
        @Named("io") ioCoroutineScope: CoroutineScope
    ): DeviceRepository = DeviceDataRepository(deviceService, ioCoroutineScope)
}