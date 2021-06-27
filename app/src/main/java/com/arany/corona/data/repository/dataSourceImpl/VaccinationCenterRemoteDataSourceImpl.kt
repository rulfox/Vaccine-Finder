package com.arany.corona.data.repository.dataSourceImpl

import com.arany.corona.api.ApiService
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource

class VaccinationCenterRemoteDataSourceImpl(private val apiService: ApiService): VaccinationCenterRemoteDataSource {
    override suspend fun getAppointments(districtId: String, date: String) {
        apiService.getVaccinationCenters(districtId, date)
    }
}