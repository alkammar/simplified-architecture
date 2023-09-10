package com.plume.domain.motion

import com.plume.domain.infra.StateUseCase
import com.plume.entity.Motion
import com.plume.repository.MotionRepository

class GetMotionUseCase(
    private val motionRepository: MotionRepository
) : StateUseCase<Unit, Motion> {

    override suspend fun onExecute(request: Unit) =
        motionRepository.flow()
}