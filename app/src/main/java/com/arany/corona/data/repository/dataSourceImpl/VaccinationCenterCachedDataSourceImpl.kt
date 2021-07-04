package com.arany.corona.data.repository.dataSourceImpl

import com.arany.corona.api.ApiService
import com.arany.corona.data.repository.dataSource.VaccinationCenterCachedDataSource
import javax.inject.Inject

class VaccinationCenterCachedDataSourceImpl @Inject constructor(private val apiService: ApiService): VaccinationCenterCachedDataSource {
}