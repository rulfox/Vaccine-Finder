package com.arany.corona

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

object PreferenceHelper {

    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
        CoreApplication.applicationContext()
    )

    private fun getSharedPreference() = sharedPreferences

    fun setDistrictId(value: String) {
        getSharedPreference().edit {
            putString("stateId", value)
        }
    }
    fun getDistrictId() = getSharedPreference().getString("stateId", "")

    fun setStateId(value: String) {
        getSharedPreference().edit {
            putString("districtId", value)
        }
    }
    fun getStateId() = getSharedPreference().getString("districtId", "")

}