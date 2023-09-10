package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.data.persistence.Persistence
import com.plume.entity.Motion
import com.plume.repository.MotionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

class MotionDataRepository(
    private val scope: CoroutineScope,
    private val persistence: Persistence
) : MotionRepository, DataRepository<Motion>(scope) {

    private var pollingJob: Job? = null

    override val emptyState = Motion.NotDetected
    override suspend fun persistedState() = null

    private var phase = 0
    private var remoteState: Motion = Motion.Disabled

    override suspend fun enableMotion(enable: Boolean) {
        remoteState = if (enable) {
            Motion.NotDetected
        } else {
            Motion.Disabled
        }.also { emit(it) }
    }

    override suspend fun remoteState(): Motion {
        delay(200)
        return when (remoteState) {
            Motion.Disabled -> remoteState
            Motion.NotDetected -> Motion.Detected("TV", 50 + (50 * sin(phase++ / 20.0 * PI)).toInt())
            is Motion.Detected -> Motion.Detected("TV", 50 + (50 * sin(phase++ / 20.0 * PI)).toInt())
        }
    }

    override fun onActive() {
        pollingJob = scope.launch {
            while (true) {
                emit {
                    delay(2000)
                    throw IllegalArgumentException()
                }
//                emit { remoteState() }
            }
        }
    }

    override fun onInactive() {
        // stop polling here
        pollingJob?.cancel()
        pollingJob = null
    }

    override fun clear() {
        super.clear()
        persistence.remove("motion")
    }
}