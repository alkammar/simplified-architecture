package com.plume.domain.motion

import com.plume.domain.infra.ActionUseCase
import com.plume.repository.MotionRepository

class ToggleMotionUseCase(
    private val motionRepository: MotionRepository
) : ActionUseCase<Boolean> {

    override suspend fun onExecute(request: Boolean) {
        motionRepository.enableMotion(request)
    }
}