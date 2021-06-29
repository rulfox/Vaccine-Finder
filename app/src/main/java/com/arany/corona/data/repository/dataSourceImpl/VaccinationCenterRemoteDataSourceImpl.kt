package com.arany.corona.data.repository.dataSourceImpl

import com.arany.corona.api.ApiService
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource
import retrofit2.Response

class VaccinationCenterRemoteDataSourceImpl(private val apiService: ApiService): VaccinationCenterRemoteDataSource {
    override suspend fun getAppointments(districtId: String, date: String): Response<VaccinationCenterResponse> {
        return apiService.getVaccinationCentersByDistrict(districtId, date)
    }
}