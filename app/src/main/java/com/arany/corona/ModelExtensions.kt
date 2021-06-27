package com.arany.corona

object ModelExtensions {
    fun ArrayList<VaccinationCenterAbstract.VaccinationCenter>.isVaccinationCenterAvailableForAge45(): Boolean {
        for (vaccinationCenter in this) {
            if (vaccinationCenter.is45PlusBookingsAvailable()) return true
        }
        return false
    }
}