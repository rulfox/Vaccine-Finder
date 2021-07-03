package com.arany.corona.data.repository.dataSourceImpl

import com.arany.corona.data.model.VaccinationCenter
import com.arany.corona.data.repository.dataSource.VaccinationCenterLocalDataSource
import com.arany.corona.db.VaccinationCenterDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VaccinationCenterLocalDataSourceImpl @Inject constructor(private val vaccinationCenterDAO: VaccinationCenterDAO): VaccinationCenterLocalDataSource {
    override suspend fun saveVaccinationCentersToDB(vaccinationCenter: VaccinationCenter) {
        vaccinationCenterDAO.insertVaccinationCenter(vaccinationCenter)
    }

    override suspend fun deleteVaccinationCenters() {
        vaccinationCenterDAO.deleteVaccinationCenters()
    }

    override fun getVaccinationCenters(): Flow<List<VaccinationCenter>> {
        return vaccinationCenterDAO.getVaccinationCenters()
    }
}