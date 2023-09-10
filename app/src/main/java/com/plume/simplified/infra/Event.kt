package com.plume.simplified.infra

open class Event<out T>(private val content: T) {

    var isConsumed = false
        private set // Allow external read but not write

    fun content() = if (isConsumed) {
        null
    } else {
        isConsumed = true
        content
    }
}