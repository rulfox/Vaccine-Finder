package com.arany.corona.presentation.di

import com.arany.corona.data.repository.LocationRepositoryImpl
import com.arany.corona.data.repository.VaccinationCenterRepositoryImpl
import com.arany.corona.data.repository.dataSource.LocationRemoteDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterCachedDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterLocalDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource
import com.arany.corona.domain.repository.LocationRepository
import com.arany.corona.domain.repository.VaccinationCenterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesLocationRepository(locationRemoteDataSource: LocationRemoteDataSource): LocationRepository {
        return LocationRepositoryImpl(locationRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesVaccinationCenterRepository(
        vaccinationCenterRemoteDataSource: VaccinationCenterRemoteDataSource,
        vaccinationCenterLocalDataSource: VaccinationCenterLocalDataSource,
        vaccinationCenterCachedDataSource: VaccinationCenterCachedDataSource
    ): VaccinationCenterRepository{
        return VaccinationCenterRepositoryImpl(vaccinationCenterRemoteDataSource, vaccinationCenterLocalDataSource, vaccinationCenterCachedDataSource)
    }
}