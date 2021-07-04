package com.arany.corona.data.repository.dataSourceImpl

import com.arany.corona.api.ApiService
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.data.repository.dataSource.LocationRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService): LocationRemoteDataSource {
    override suspend fun getState(): Response<StateResponse> {
        return apiService.getStates()
    }

    override suspend fun getDistricts(stateId: String): Response<DistrictResponse> {
        return apiService.getDistricts(stateId)
    }

}