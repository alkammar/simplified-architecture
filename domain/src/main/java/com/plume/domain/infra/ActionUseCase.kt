package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class ActionUseCase<REQUEST>(
    private var mainCoroutineScope: CoroutineScope,
    private var ioCoroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST, onError: (Throwable) -> Unit = {}) {
        mainCoroutineScope.launch {
           try {
               onExecute(request)
           } catch (e: Exception) {
               ioCoroutineScope.launch {
                   onError(e)
               }
           }
        }
    }

    protected abstract suspend fun onExecute(request: REQUEST)
}