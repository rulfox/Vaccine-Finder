package com.arany.corona.data.model

import com.google.gson.annotations.SerializedName

data class VaccinationCenterResponse(
    @SerializedName("sessions")
    val vaccinationCenters: ArrayList<VaccinationCenter>?
)