package com.arany.corona.domain.useCase

import com.arany.corona.data.model.VaccinationCenter
import com.arany.corona.domain.repository.VaccinationCenterRepository

class SaveVaccinationCentersByDistrictUseCase(private val vaccinationCenterRepository: VaccinationCenterRepository) {
    suspend fun execute(vaccinationCenter: VaccinationCenter) = vaccinationCenterRepository.saveVaccinationCenter(vaccinationCenter)
}