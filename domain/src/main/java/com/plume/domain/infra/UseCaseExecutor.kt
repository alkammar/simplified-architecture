package com.plume.domain.infra

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UseCaseExecutor(
    private var mainCoroutineScope: CoroutineScope,
    private var ioCoroutineScope: CoroutineScope
) {

    fun <REQUEST, STATE> execute(
        stateUseCase: StateUseCase<REQUEST, STATE>,
        request: REQUEST,
        onState: (STATE) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) = ioCoroutineScope.launch {
            stateUseCase.onExecute(request)
                .onEach { state -> mainCoroutineScope.launch { onState(state) } }
                .catch { throwable -> mainCoroutineScope.launch { onError(throwable) } }
                .collect()
        }

    fun <REQUEST> execute(
        actionUseCase: ActionUseCase<REQUEST>,
        request: REQUEST,
        onError: (Throwable) -> Unit = {}
    ) {
        ioCoroutineScope.launch {
            try {
                actionUseCase.onExecute(request)
            } catch (e: Exception) {
                mainCoroutineScope.launch { onError(e) }
            }
        }
    }
}