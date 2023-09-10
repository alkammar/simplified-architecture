package com.plume.domain.motion

import com.plume.domain.infra.StateUseCase
import com.plume.entity.Motion
import com.plume.repository.MotionRepository
import kotlinx.coroutines.CoroutineScope

class GetMotionUseCase(
    private val motionRepository: MotionRepository,
    mainCoroutineScope: CoroutineScope,
    ioCoroutineScope: CoroutineScope
) : StateUseCase<Unit, Motion>(mainCoroutineScope, ioCoroutineScope) {

    override suspend fun onExecute(request: Unit) =
        motionRepository.flow()
}