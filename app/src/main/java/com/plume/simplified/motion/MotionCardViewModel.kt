package com.plume.simplified.motion

import com.plume.domain.motion.GetMotionUseCase
import com.plume.domain.motion.ToggleMotionUseCase
import com.plume.entity.Motion
import com.plume.simplified.infra.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MotionCardViewModel @Inject constructor(
    getMotionUseCase: GetMotionUseCase,
    private val toggleMotionUseCase: ToggleMotionUseCase
) : BaseViewModel<Unit, Motion>() {

    override val initialState = Motion.NotDetected
    override val stateUseCase = getMotionUseCase

    fun onToggleMotionAction(checked: Boolean) {
        toggleMotionUseCase.execute(checked)
    }
}