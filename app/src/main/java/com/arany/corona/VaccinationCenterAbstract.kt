package com.arany.corona


import com.google.gson.annotations.SerializedName

data class VaccinationCenterAbstract(
    @SerializedName("sessions")
    val vaccinationCenters: ArrayList<VaccinationCenter>?
) {
    data class VaccinationCenter(
        @SerializedName("address")
        val address: String?,
        @SerializedName("address_l")
        val addressL: String?,
        @SerializedName("available_capacity")
        val availableCapacity: Int?,
        @SerializedName("available_capacity_dose1")
        val availableCapacityDose1: Int?,
        @SerializedName("available_capacity_dose2")
        val availableCapacityDose2: Int?,
        @SerializedName("block_name")
        val blockName: String?,
        @SerializedName("block_name_l")
        val blockNameL: String?,
        @SerializedName("center_id")
        val centerId: Int?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("district_name")
        val districtName: String?,
        @SerializedName("district_name_l")
        val districtNameL: String?,
        @SerializedName("fee")
        val fee: String?,
        @SerializedName("fee_type")
        val feeType: String?,
        @SerializedName("from")
        val from: String?,
        @SerializedName("lat")
        val lat: Double?,
        @SerializedName("long")
        val long: Double?,
        @SerializedName("min_age_limit")
        val minAgeLimit: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("name_l")
        val nameL: String?,
        @SerializedName("pincode")
        val pincode: String?,
        @SerializedName("session_id")
        val sessionId: String?,
        @SerializedName("slots")
        val slots: List<String?>?,
        @SerializedName("state_name")
        val stateName: String?,
        @SerializedName("state_name_l")
        val stateNameL: String?,
        @SerializedName("to")
        val to: String?,
        @SerializedName("vaccine")
        val vaccine: String?
    ){
        fun is45PlusBookingsAvailable(): Boolean{
            return (minAgeLimit!! >= 45 && availableCapacityDose1!! > 0)
        }
    }
}