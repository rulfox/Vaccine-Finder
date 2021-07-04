package com.arany.corona.api

import com.arany.corona.DistrictAbstract
import com.arany.corona.StateAbstract
import com.arany.corona.VaccinationCenterAbstract
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("api/v2/admin/location/states")
    suspend fun getStates(): Response<StateResponse>

    @GET("api/v2/admin/location/districts/{stateId}")
    suspend fun getDistricts(@Path("stateId") stateId: String): Response<DistrictResponse>

    @GET("api/v2/appointment/sessions/public/findByDistrict")
    suspend fun getVaccinationCentersByDistrict(@Query("district_id") districtId: String?, @Query("date") date: String?): Response<VaccinationCenterResponse>

    @GET("api/v2/appointment/sessions/public/findByDistrict")
    suspend fun getVaccinationCenters(@Query("district_id") districtId: String?, @Query("date") date: String?): Response<VaccinationCenterAbstract>
}