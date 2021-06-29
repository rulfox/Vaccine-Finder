package com.arany.corona.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arany.corona.data.model.VaccinationCenter

@Database(entities = [VaccinationCenter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VaccinationCenterDatabase: RoomDatabase() {
    abstract fun getVaccinationCenterDAO(): VaccinationCenterDAO
}