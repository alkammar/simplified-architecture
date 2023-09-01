package com.plume.data

import com.plume.data.infra.DataRepository
import com.plume.entity.Node
import com.plume.repository.NodeRepository

class NodeDataRepository :
    NodeRepository,
    DataRepository<List<Node>>() {

    override val initialState = listOf(
        Node("73", "The escape room")
    )
}