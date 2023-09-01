package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class ActionUseCase<REQUEST>(
    private var coroutineScope: CoroutineScope
) {
    fun execute(request: REQUEST) {
        coroutineScope.launch { onExecute(request) }
    }

    abstract suspend fun onExecute(request: REQUEST)
}