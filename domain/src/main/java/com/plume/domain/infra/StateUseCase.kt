package com.plume.domain.infra

import kotlinx.coroutines.flow.Flow

interface StateUseCase<REQUEST, STATE> {
    suspend fun onExecute(request: REQUEST): Flow<STATE>
}