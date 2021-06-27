package com.arany.corona.db

import androidx.room.*
import com.arany.corona.data.model.VaccinationCenter
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinationCenterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccinationCenter(vaccinationCenter: VaccinationCenter)

    @Query("DELETE FROM VaccinationCenter")
    suspend fun deleteVaccinationCenters()

    @Query("SELECT * FROM VaccinationCenter")
    fun getVaccinationCenters(): Flow<List<VaccinationCenter>>
}