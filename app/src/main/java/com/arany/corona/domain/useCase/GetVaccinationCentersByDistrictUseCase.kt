package com.arany.corona.domain.useCase

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.domain.repository.VaccinationCenterRepository
import javax.inject.Inject

class GetVaccinationCentersByDistrictUseCase @Inject constructor(private val vaccinationCenterRepository: VaccinationCenterRepository) {
    suspend fun execute(districtId: String, date: String): Resource<VaccinationCenterResponse> {
        return vaccinationCenterRepository.getAppointmentsByDistrict(districtId, date)
    }
}