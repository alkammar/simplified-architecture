package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class StateUseCase<REQUEST, STATE>(
    private var coroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST, callback: (STATE) -> Unit = {}) =
        coroutineScope.launch {
            onExecute(request)
                .collect { state ->
                    withContext(Dispatchers.Main) {
                        callback(state)
                    }
                }
        }

    abstract suspend fun onExecute(request: REQUEST): Flow<STATE>
}