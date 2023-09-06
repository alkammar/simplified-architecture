package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.data.persistence.Persistence
import com.plume.entity.Node
import com.plume.repository.NodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NodeDataRepository(
    private val scope: CoroutineScope,
    private val persistence: Persistence
) : NodeRepository, DataRepository<List<Node>>(scope) {

    private var pollingJob: Job? = null

    override val emptyState = emptyList<Node>()
    override suspend fun persistedState() = null

    override suspend fun remoteState(): List<Node> {
        delay(1000)
        if (count++ == 5) {
            throw IllegalArgumentException()
        } else {
            return listOf(
                Node("73", "The bad room")
            )
        }
    }

    var count = 0

    override fun onActive() {
        pollingJob = scope.launch {
            while (true) {
                delay(2000)
                emit { remoteState() }
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
        persistence.remove("nodes")
    }
}