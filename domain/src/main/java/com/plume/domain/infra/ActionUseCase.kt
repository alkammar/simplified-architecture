package com.plume.domain.infra

interface ActionUseCase<REQUEST> {
    suspend fun onExecute(request: REQUEST)
}