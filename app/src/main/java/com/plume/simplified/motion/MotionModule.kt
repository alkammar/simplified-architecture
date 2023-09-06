package com.plume.simplified.motion

import com.plume.domain.motion.GetMotionUseCase
import com.plume.domain.motion.ToggleMotionUseCase
import com.plume.repository.MotionRepository
import com.plume.simplified.application.Scope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MotionModule {

    @Provides
    fun providesGetMotionUseCase(
        motionRepository: MotionRepository,
        scope: Scope
    ) = GetMotionUseCase(motionRepository, scope.mainCoroutineScope)

    @Provides
    fun providesToggleMotionUseCase(
        motionRepository: MotionRepository,
        scope: Scope
    ) = ToggleMotionUseCase(motionRepository, scope.mainCoroutineScope)
}