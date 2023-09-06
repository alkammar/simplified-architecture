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

    private val resolvableStateFlow by lazy { MutableStateFlow(suspend { persistedState() ?: emptyState }) }
    private val resolvedStateFlow by lazy { MutableStateFlow(emptyState) }

    private var isStale = true
    abstract val emptyState: STATE
    abstract suspend fun persistedState(): STATE?
    abstract suspend fun remoteState(): STATE

    init {
        scope.launch {
            resolvableStateFlow
                .take(1)
                .onCompletion {
                    println("onCompletion of persistence load")
                    resolvableStateFlow.emit(suspend { remoteState() })
                }
                .collect()
        }

        scope.launch {
            resolvableStateFlow
                .subscriptionCount
                .map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) onActive() else onInactive()
                }
                .collect()
        }
    }

    override suspend fun flow() = resolvableStateFlow
        .map { it() }
        .onEach { resolvedStateFlow.emit(it) }

    protected fun latest() = resolvedStateFlow.value

    protected suspend fun emit(state: STATE) {
        resolvableStateFlow.emit(suspend { state })
    }

    protected suspend fun emit(resolvableState: suspend () -> STATE) {
        resolvableStateFlow.emit(resolvableState)
    }

    open fun onInactive() {}

    open fun onActive() {}

    override fun clear() {
        resolvableStateFlow.resetReplayCache()
        resolvedStateFlow.resetReplayCache()
    }
}