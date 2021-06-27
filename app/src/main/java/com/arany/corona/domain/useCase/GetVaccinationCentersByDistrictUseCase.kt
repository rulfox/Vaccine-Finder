package com.arany.corona.domain.useCase

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.domain.repository.VaccinationCenterRepository

class GetVaccinationCentersByDistrictUseCase(private val vaccinationCenterRepository: VaccinationCenterRepository) {
    suspend fun execute(districtId: String, date: String): Resource<VaccinationCenterResponse> {
        return vaccinationCenterRepository.getAppointmentsByDistrict(districtId, date)
    }
}