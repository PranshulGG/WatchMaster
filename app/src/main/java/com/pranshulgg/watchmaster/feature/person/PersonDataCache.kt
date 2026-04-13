package com.pranshulgg.watchmaster.feature.person

object PersonDataCache {
    private val cache = mutableMapOf<Long, PersonEntity>()

    fun get(id: Long): PersonEntity? {
        return cache[id]
    }

    fun put(id: Long, person: PersonEntity?) {
        if (person != null)
            cache[id] = person
    }
}