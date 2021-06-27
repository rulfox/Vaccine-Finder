package com.arany.corona.domain.useCase

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.domain.repository.LocationRepository
import com.arany.corona.domain.repository.VaccinationCenterRepository

class GetDistrictsUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute(stateId: String): Resource<DistrictResponse> {
        return locationRepository.getDistricts(stateId)
    }
}