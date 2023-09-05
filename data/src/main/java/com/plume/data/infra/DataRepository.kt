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
    protected val stateFlow by lazy { MutableStateFlow(emptyState) }

    private var isInitialized = false
    abstract val emptyState: STATE
    abstract suspend fun persistedState(): STATE?
    abstract suspend fun remoteState(): STATE

    override suspend fun flow() = stateFlow.onInitialize()

    private suspend fun MutableStateFlow<STATE>.onInitialize(): MutableStateFlow<STATE> {
        if (!isInitialized) {
            emit(persistedState() ?: emptyState)
            emit(remoteState())
            isInitialized = true
        }
        return this
    }

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

    override fun clear() {
        stateFlow.resetReplayCache()
    }
}