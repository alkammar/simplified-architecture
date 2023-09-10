package com.plume.simplified.motion

import com.plume.domain.motion.GetMotionUseCase
import com.plume.domain.motion.ToggleMotionUseCase
import com.plume.repository.MotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class MotionModule {

    @Provides
    fun providesGetMotionUseCase(
        motionRepository: MotionRepository,
        @Named("io") ioCoroutineScope: CoroutineScope
    ) = GetMotionUseCase(motionRepository, ioCoroutineScope)

    @Provides
    fun providesToggleMotionUseCase(
        motionRepository: MotionRepository,
        @Named("io") mainCoroutineScope: CoroutineScope,
        @Named("main") ioCoroutineScope: CoroutineScope
    ) = ToggleMotionUseCase(motionRepository, mainCoroutineScope, ioCoroutineScope)
}