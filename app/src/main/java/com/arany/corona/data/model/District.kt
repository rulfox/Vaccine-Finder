package com.arany.corona.data.model

import com.google.gson.annotations.SerializedName

data class District(
    @SerializedName("district_id")
    val districtId: Int?,
    @SerializedName("district_name")
    val districtName: String?
)