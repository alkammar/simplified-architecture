package com.plume.simplified.application

import android.content.SharedPreferences
import com.google.gson.Gson
import com.plume.data.persistence.Persistence

class DataPersistence(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : Persistence {
    override fun <T> objectList(key: String, cls: Class<T>): List<T>? {
        val serializedNodes = sharedPreferences.getStringSet(key, null)
        return serializedNodes?.map { gson.fromJson(it, cls) }
    }

    override fun <T> saveObjectList(key: String, cls: Class<T>, list: List<T>) {
        sharedPreferences.edit()
            .putStringSet(
                key,
                list.map { item -> gson.toJson(item) }.toSet()
            ).apply()

    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}