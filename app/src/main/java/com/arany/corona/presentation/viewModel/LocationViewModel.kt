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
import com.arany.corona.data.model.DistrictResponse
import com.arany.corona.data.model.StateResponse
import com.arany.corona.data.model.VaccinationCenterResponse
import com.arany.corona.domain.useCase.GetDistrictsUseCase
import com.arany.corona.domain.useCase.GetStatesUseCase
import com.arany.corona.domain.useCase.GetVaccinationCentersByDistrictUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(
    private val app: Application,
    private val getStatesUseCase: GetStatesUseCase,
    private val getDistrictsUseCase: GetDistrictsUseCase): AndroidViewModel(app) {

    //Find States
    val states: MutableLiveData<Resource<StateResponse>> = MutableLiveData()
    fun getVaccinationStates() = viewModelScope.launch(Dispatchers.IO) {
        states.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val apiResult = getStatesUseCase.execute()
                states.postValue(apiResult)
            } else {
                states.postValue(Resource.Error("Internet not available"))
            }
        }catch (e: Exception){
            states.postValue(Resource.Error(e.message.toString()))
        }
    }

    //Find Districts
    val districts: MutableLiveData<Resource<DistrictResponse>> = MutableLiveData()
    fun getVaccinationDistricts(stateId: String) = viewModelScope.launch(Dispatchers.IO) {
        districts.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val apiResult = getDistrictsUseCase.execute(stateId)
                districts.postValue(apiResult)
            } else {
                districts.postValue(Resource.Error("Internet not available"))
            }
        }catch (e: Exception){
            districts.postValue(Resource.Error(e.message.toString()))
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