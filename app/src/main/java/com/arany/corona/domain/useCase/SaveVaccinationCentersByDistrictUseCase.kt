package com.arany.corona.domain.useCase

import com.arany.corona.data.model.VaccinationCenter
import com.arany.corona.domain.repository.VaccinationCenterRepository
import javax.inject.Inject

class SaveVaccinationCentersByDistrictUseCase @Inject constructor(private val vaccinationCenterRepository: VaccinationCenterRepository) {
    suspend fun execute(vaccinationCenter: VaccinationCenter) = vaccinationCenterRepository.saveVaccinationCenter(vaccinationCenter)
}