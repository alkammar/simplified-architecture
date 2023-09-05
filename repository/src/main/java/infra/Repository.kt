package infra

import kotlinx.coroutines.flow.Flow

interface Repository<STATE> {
    suspend fun flow(): Flow<STATE>
    fun clear()
}