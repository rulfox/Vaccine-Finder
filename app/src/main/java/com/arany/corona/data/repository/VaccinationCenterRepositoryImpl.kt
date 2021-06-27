package com.arany.corona.data.repository

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.data.model.VaccinationCenter
import com.arany.corona.data.repository.dataSource.VaccinationCenterCachedDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterLocalDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource
import com.arany.corona.domain.repository.VaccinationCenterRepository
import retrofit2.Response

class VaccinationCenterRepositoryImpl(
    private val vaccinationCenterRemoteDataSource: VaccinationCenterRemoteDataSource,
    private val vaccinationCenterLocalDataSource: VaccinationCenterLocalDataSource,
    private val vaccinationCenterCachedDataSource: VaccinationCenterCachedDataSource
): VaccinationCenterRepository {
    override suspend fun getAppointmentsByDistrict(districtId: String, date: String): Resource<VaccinationCenterResponse> {
        val response: Response<VaccinationCenterResponse> = vaccinationCenterRemoteDataSource.getAppointments(districtId, date)
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveVaccinationCenter(vaccinationCenter: VaccinationCenter) {
        vaccinationCenterLocalDataSource.saveVaccinationCentersToDB(vaccinationCenter)
    }
}