package com.plume.data.infra

import infra.Repository
import kotlinx.coroutines.flow.MutableStateFlow

abstract class DataRepository<STATE> : Repository<STATE> {
    abstract val initialState: STATE
    protected val stateFlow by lazy { MutableStateFlow(initialState) }

    override fun flow() = stateFlow
}