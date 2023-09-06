package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class StateUseCase<REQUEST, STATE>(
    private var coroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST, onState: (STATE) -> Unit, onError: (Throwable) -> Unit = {}) =
        coroutineScope.launch {
            onExecute(request)
                .onEach { state -> withContext(Dispatchers.Main) { onState(state) } }
                .catch { throwable -> onError(throwable) }
                .collect()
        }

    abstract suspend fun onExecute(request: REQUEST): Flow<STATE>
}