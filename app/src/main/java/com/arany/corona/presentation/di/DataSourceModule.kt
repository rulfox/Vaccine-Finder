
package com.arany.corona.presentation.di

import com.arany.corona.api.ApiService
import com.arany.corona.data.repository.LocationRepositoryImpl
import com.arany.corona.data.repository.VaccinationCenterRepositoryImpl
import com.arany.corona.data.repository.dataSource.LocationRemoteDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterCachedDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterLocalDataSource
import com.arany.corona.data.repository.dataSource.VaccinationCenterRemoteDataSource
import com.arany.corona.data.repository.dataSourceImpl.LocationRemoteDataSourceImpl
import com.arany.corona.data.repository.dataSourceImpl.VaccinationCenterCachedDataSourceImpl
import com.arany.corona.data.repository.dataSourceImpl.VaccinationCenterLocalDataSourceImpl
import com.arany.corona.data.repository.dataSourceImpl.VaccinationCenterRemoteDataSourceImpl
import com.arany.corona.db.VaccinationCenterDAO
import com.arany.corona.domain.repository.LocationRepository
import com.arany.corona.domain.repository.VaccinationCenterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {
    @Singleton
    @Provides
    fun providesLocationRemoteDataSource(
        apiService: ApiService
    ): LocationRemoteDataSource {
        return LocationRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesVaccinationCenterCachedDataSource(
        apiService: ApiService
    ): VaccinationCenterCachedDataSource {
        return VaccinationCenterCachedDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesVaccinationCenterLocalDataSource(
        vaccinationCenterDAO: VaccinationCenterDAO
    ): VaccinationCenterLocalDataSource {
        return VaccinationCenterLocalDataSourceImpl(vaccinationCenterDAO)
    }

    @Singleton
    @Provides
    fun providesVaccinationCenterRemoteDataSourceImpl(
        apiService: ApiService
    ): VaccinationCenterRemoteDataSource {
        return VaccinationCenterRemoteDataSourceImpl(apiService)
    }
}