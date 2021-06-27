package com.arany.corona


import com.google.gson.annotations.SerializedName

data class DistrictAbstract(
    @SerializedName("districts")
    val districts: List<District>?,
    @SerializedName("ttl")
    val ttl: Int?
) {
    data class District(
        @SerializedName("district_id")
        val districtId: Int?,
        @SerializedName("district_name")
        val districtName: String?
    )
}