package com.plume.data.infra

import infra.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class DataRepository<STATE>(scope: CoroutineScope) : Repository<STATE> {
    abstract val initialState: STATE
    protected val stateFlow by lazy { MutableStateFlow(initialState) }

    override fun flow() = stateFlow

    init {
        scope.launch {
            stateFlow.subscriptionCount
                .map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) onActive() else onInactive()
                }
                .collect()
        }
    }

    open fun onInactive() {}

    open fun onActive() {}
}