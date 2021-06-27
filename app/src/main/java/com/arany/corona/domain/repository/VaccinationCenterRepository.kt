package com.arany.corona.domain.repository

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.data.model.VaccinationCenter

interface VaccinationCenterRepository {
    suspend fun getAppointmentsByDistrict(districtId: String, date: String): Resource<VaccinationCenterResponse>
    suspend fun saveVaccinationCenter(vaccinationCenter: VaccinationCenter)
}