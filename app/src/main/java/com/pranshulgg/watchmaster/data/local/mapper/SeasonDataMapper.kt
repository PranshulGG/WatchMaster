package com.pranshulgg.watchmaster.data.local.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pranshulgg.watchmaster.model.SeasonDataModel

object SeasonDataMapper {

    private val gson = Gson()
    private val type = object : TypeToken<List<SeasonDataModel>>() {}.type

    fun toJson(seasons: List<SeasonDataModel>?): String? {
        if (seasons.isNullOrEmpty()) return null
        return gson.toJson(seasons)
    }

    fun fromJson(json: String?): List<SeasonDataModel> {
        if (json.isNullOrBlank()) return emptyList()

        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
