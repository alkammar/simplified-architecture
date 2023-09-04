package com.plume.simplified.application

import com.plume.data.DeviceDataRepository
import com.plume.data.NodeDataRepository
import com.plume.repository.DeviceRepository
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCoroutineScope() = Scope()

    @Provides
    @Singleton
    fun providesDeviceRepository(scope: Scope): DeviceRepository = DeviceDataRepository(scope.mainCoroutineScope)

    @Provides
    @Singleton
    fun providesNodeRepository(scope: Scope): NodeRepository = NodeDataRepository(scope.mainCoroutineScope)
}