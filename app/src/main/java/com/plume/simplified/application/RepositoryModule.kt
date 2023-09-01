package com.plume.simplified.application

import com.plume.data.DeviceDataRepository
import com.plume.data.NodeDataRepository
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCoroutineScope() = CoroutineScope(Dispatchers.Main)

    @Provides
    @Singleton
    fun providesDeviceRepository(): DeviceRepository = DeviceDataRepository()

    @Provides
    @Singleton
    fun providesNodeRepository(): NodeRepository = NodeDataRepository()
}