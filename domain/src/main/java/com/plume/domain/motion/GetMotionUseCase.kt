package com.plume.domain.motion

import com.plume.domain.infra.StateUseCase
import com.plume.entity.Motion
import com.plume.repository.MotionRepository
import kotlinx.coroutines.CoroutineScope

class GetMotionUseCase(
    private val motionRepository: MotionRepository,
    coroutineScope: CoroutineScope
) : StateUseCase<Unit, Motion>(coroutineScope) {

    override suspend fun onExecute(request: Unit) =
        motionRepository.flow()
}