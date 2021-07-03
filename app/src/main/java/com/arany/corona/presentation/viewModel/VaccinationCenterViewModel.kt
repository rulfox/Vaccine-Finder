package com.arany.corona.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.domain.useCase.GetVaccinationCentersByDistrictUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class VaccinationCenterViewModel(
    private val app: Application,
    private val getVaccinationCentersByDistrictUseCase: GetVaccinationCentersByDistrictUseCase): AndroidViewModel(app) {

    //Find vaccination ceneters
    val vaccinationCenters: MutableLiveData<Resource<VaccinationCenterResponse>> = MutableLiveData()
    fun getVaccinationCenters(districtId: String, date: String) = viewModelScope.launch(Dispatchers.IO) {
        vaccinationCenters.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val apiResult = getVaccinationCentersByDistrictUseCase.execute(districtId, date)
                vaccinationCenters.postValue(apiResult)
            } else {
                vaccinationCenters.postValue(Resource.Error("Internet not available"))
            }
        }catch (e: Exception){
            vaccinationCenters.postValue(Resource.Error(e.message.toString()))
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}