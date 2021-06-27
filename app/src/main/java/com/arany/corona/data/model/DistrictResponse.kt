package com.arany.corona.data.model

import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("districts")
    val districts: List<District>?,
    @SerializedName("ttl")
    val ttl: Int?
)