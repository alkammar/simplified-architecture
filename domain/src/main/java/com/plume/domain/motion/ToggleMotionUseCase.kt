package com.plume.domain.motion

import com.plume.domain.infra.ActionUseCase
import com.plume.repository.MotionRepository
import kotlinx.coroutines.CoroutineScope

class ToggleMotionUseCase(
    private val motionRepository: MotionRepository,
    coroutineScope: CoroutineScope
) : ActionUseCase<Boolean>(coroutineScope) {

    override suspend fun onExecute(request: Boolean) {
        motionRepository.enableMotion(request)
    }
}