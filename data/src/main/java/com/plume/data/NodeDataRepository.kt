package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.data.persistence.Persistence
import com.plume.entity.Node
import com.plume.repository.NodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NodeDataRepository(
    private val scope: CoroutineScope,
    private val persistence: Persistence
) : NodeRepository, DataRepository<List<Node>>(scope) {

    private var pollingJob: Job? = null

    override val emptyState = emptyList<Node>()
    override suspend fun persistedState() = null

    override suspend fun remoteState(): List<Node> {
        delay(2000)
        return listOf(
            Node("73", "The bad room")
        )
    }

    override fun onActive() {
        pollingJob = scope.launch {
            while (true) {
//                with(remote()) {
//                    stateFlow.emit(this)
//                    persistence.saveObjectList("nodes", Node::class.java, this)
//                }
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