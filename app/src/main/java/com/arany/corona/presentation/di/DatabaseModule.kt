package com.arany.corona.presentation.di

import android.app.Application
import androidx.room.Room
import com.arany.corona.db.VaccinationCenterDAO
import com.arany.corona.db.VaccinationCenterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesVaccinationCenterDatabase(application: Application): VaccinationCenterDatabase {
        return Room.databaseBuilder(application, VaccinationCenterDatabase::class.java, "vaccination_center_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesVaccinationCenterDao(vaccinationCenterDatabase: VaccinationCenterDatabase): VaccinationCenterDAO{
        return vaccinationCenterDatabase.getVaccinationCenterDAO()
    }
}