package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.feature.person.PersonEntity

class PersonRepository(
    private val api: TmdbApi
) {

    suspend fun fetchPersonData(personId: Long): PersonEntity? {
        val response = api.getPersonData(personId)

        val body = response.body() ?: return null

        return body
    }

}