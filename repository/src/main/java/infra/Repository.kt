package infra

import kotlinx.coroutines.flow.Flow

interface Repository<STATE> {
    fun flow(): Flow<STATE>
}