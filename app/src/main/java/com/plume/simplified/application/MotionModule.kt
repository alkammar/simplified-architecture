package com.plume.simplified.application

import com.plume.data.MotionDataRepository
import com.plume.data.persistence.Persistence
import com.plume.repository.MotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MotionModule {
    @Provides
    @Singleton
    fun providesMotionRepository(
        @Named("io") ioCoroutineScope: CoroutineScope,
        persistence: Persistence
    ): MotionRepository = MotionDataRepository(ioCoroutineScope, persistence)
}