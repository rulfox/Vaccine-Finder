package com.arany.corona.data.model

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("state_id")
    val stateId: Int?,
    @SerializedName("state_name")
    val stateName: String?
)
