package com.arany.corona.data.repository.dataSource

import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import retrofit2.Response

interface LocationRemoteDataSource {
    suspend fun getState(): Response<StateResponse>
    suspend fun getDistricts(stateId: String): Response<DistrictResponse>
}