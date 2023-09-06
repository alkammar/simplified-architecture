package com.plume.entity

sealed class Motion {
    object Disabled: Motion()
    object NotDetected: Motion()
    data class Detected(val source: String, val level: Int): Motion()
}
