package com.pranshulgg.watchmaster.feature.person

import com.google.gson.annotations.SerializedName

data class PersonEntity(
    val name: String,
    val biography: String,
    val birthday: String,
    val gender: Int,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("place_of_birth")
    val placeOfBirth: String,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    val deathday: String? = null
)