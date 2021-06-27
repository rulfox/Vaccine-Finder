package com.arany.corona.data.repository.dataSource

import com.arany.corona.data.model.VaccinationCenterResponse
import retrofit2.Response

interface VaccinationCenterRemoteDataSource {
    suspend fun getAppointments(districtId: String, date: String): Response<VaccinationCenterResponse>
}