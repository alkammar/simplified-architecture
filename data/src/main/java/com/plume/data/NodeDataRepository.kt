package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.entity.Node
import com.plume.repository.NodeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NodeDataRepository(private val scope: CoroutineScope) :
    NodeRepository,
    DataRepository<List<Node>>(scope) {

    private var pollingJob: Job? = null

    override val initialState = listOf(
        Node("73", "The escape room")
    )

    override fun onActive() {
        // start polling here
        pollingJob = scope.launch {
            while (true) {
                delay(2000)
                println("polling node data")
            }
        }
    }

    override fun onInactive() {
        // stop polling here
        pollingJob?.cancel()
        pollingJob = null
    }
}