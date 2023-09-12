package com.plume.simplified.application

import com.plume.data.NodeDataRepository
import com.plume.data.persistence.Persistence
import com.plume.repository.NodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NodeModule {
    @Provides
    @Singleton
    fun providesNodeRepository(
        @Named("io") ioCoroutineScope: CoroutineScope,
        persistence: Persistence
    ): NodeRepository = NodeDataRepository(ioCoroutineScope, persistence)
}