package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class StateUseCase<REQUEST, STATE>(
    private var coroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST, callback: (STATE) -> Unit = {}): Job {
        return coroutineScope.launch { onExecute(request).collect { state -> callback(state) } }
    }

    abstract suspend fun onExecute(request: REQUEST): Flow<STATE>
}