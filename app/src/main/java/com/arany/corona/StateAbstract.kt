package com.arany.corona


import com.google.gson.annotations.SerializedName

data class StateAbstract(
    @SerializedName("states")
    val states: ArrayList<State>?,
    @SerializedName("ttl")
    val ttl: Int?
) {
    data class State(
        @SerializedName("state_id")
        val stateId: Int?,
        @SerializedName("state_name")
        val stateName: String?
    )
}