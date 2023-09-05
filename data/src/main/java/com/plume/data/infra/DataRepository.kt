package com.plume.data.infra

import infra.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

abstract class DataRepository<STATE>(scope: CoroutineScope) : Repository<STATE> {

    private val stateFlow by lazy { MutableStateFlow(suspend { persistedState() ?: emptyState }) }
    private val resolvedFlow by lazy { MutableStateFlow(emptyState) }

    private var isStale = true
    abstract val emptyState: STATE
    abstract suspend fun persistedState(): STATE?
    abstract suspend fun remoteState(): STATE

    init {
        scope.launch {
            stateFlow
                .take(1)
                .onCompletion {
                    println("onCompletion of persistence load")
                    stateFlow.emit(suspend { remoteState() })
                }
                .collect()
        }

        scope.launch {
            stateFlow
                .subscriptionCount
                .map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) onActive() else onInactive()
                }
                .collect()
        }
    }

    override suspend fun flow() = stateFlow
        .map { it() }
        .onEach { resolvedFlow.emit(it) }

    protected fun latest() = resolvedFlow.value

    protected suspend fun emit(state: STATE) {
        stateFlow.emit(suspend { state })
    }

    open fun onInactive() {}

    open fun onActive() {}

    override fun clear() {
        stateFlow.resetReplayCache()
        resolvedFlow.resetReplayCache()
    }
}