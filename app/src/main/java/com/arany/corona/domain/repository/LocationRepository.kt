package com.arany.corona.domain.repository

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import retrofit2.Response

interface LocationRepository {
    suspend fun getStates(): Resource<StateResponse>
    suspend fun getDistricts(stateId: String): Resource<DistrictResponse>
}