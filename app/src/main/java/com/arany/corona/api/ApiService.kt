package com.arany.corona.api

import com.arany.corona.DistrictAbstract
import com.arany.corona.StateAbstract
import com.arany.corona.VaccinationCenterAbstract
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("api/v2/admin/location/states")
    suspend fun getStates(): Response<StateAbstract>

    @GET("api/v2/admin/location/districts/{stateId}")
    suspend fun getDistricts(@Path("stateId") stateId: String): Response<DistrictAbstract>

    @GET("api/v2/appointment/sessions/public/findByDistrict")
    suspend fun getVaccinationCenters(@Query("district_id") districtId: String?, @Query("date") date: String?): Response<VaccinationCenterAbstract>
}