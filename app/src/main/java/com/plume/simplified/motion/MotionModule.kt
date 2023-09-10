package com.plume.simplified.motion

import com.plume.domain.motion.GetMotionUseCase
import com.plume.domain.motion.ToggleMotionUseCase
import com.plume.repository.MotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MotionModule {

    @Provides
    fun providesGetMotionUseCase(
        motionRepository: MotionRepository
    ) = GetMotionUseCase(motionRepository)

    @Provides
    fun providesToggleMotionUseCase(
        motionRepository: MotionRepository
    ) = ToggleMotionUseCase(motionRepository)
}