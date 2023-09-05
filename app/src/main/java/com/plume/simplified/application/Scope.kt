package com.plume.simplified.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Scope {
    val mainCoroutineScope = CoroutineScope(Dispatchers.IO)
}