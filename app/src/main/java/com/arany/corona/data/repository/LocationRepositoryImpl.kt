package com.arany.corona.data.repository

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.data.repository.dataSource.LocationRemoteDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource
import com.arany.corona.domain.repository.LocationRepository
import retrofit2.Response
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource
    ): LocationRepository {
    override suspend fun getStates(): Resource<StateResponse> {
        val response: Response<StateResponse> = locationRemoteDataSource.getState()
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getDistricts(stateId: String): Resource<DistrictResponse> {
        val response: Response<DistrictResponse> = locationRemoteDataSource.getDistricts(stateId)
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}