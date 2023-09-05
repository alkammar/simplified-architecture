package com.plume.data.persistence

interface Persistence {
    fun <T> objectList(key: String, cls: Class<T>): List<T>?
    fun <T> saveObjectList(key: String, cls: Class<T>, list: List<T>)
    fun remove(key: String)
}