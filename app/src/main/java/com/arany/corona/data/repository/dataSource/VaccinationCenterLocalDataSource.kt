package com.arany.corona.data.repository.dataSource

import com.arany.corona.data.model.VaccinationCenter
import kotlinx.coroutines.flow.Flow

interface VaccinationCenterLocalDataSource {
    suspend fun saveVaccinationCentersToDB(vaccinationCenter: VaccinationCenter)
    suspend fun deleteVaccinationCenters()
    fun getVaccinationCenters(): Flow<List<VaccinationCenter>>
}