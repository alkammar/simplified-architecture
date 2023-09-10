package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class StateUseCase<REQUEST, STATE>(
    private val mainCoroutineScope: CoroutineScope,
    private val ioCoroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST, onState: (STATE) -> Unit, onError: (Throwable) -> Unit = {}) =
        ioCoroutineScope.launch {
            onExecute(request)
                .onEach { state -> mainCoroutineScope.launch { onState(state) } }
                .catch { throwable -> mainCoroutineScope.launch { onError(throwable) } }
                .collect()
        }

    abstract suspend fun onExecute(request: REQUEST): Flow<STATE>
}