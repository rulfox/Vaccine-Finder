package com.arany.corona

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arany.corona.api.ApiService
import com.arany.corona.databinding.ActivityMainBinding
import com.arany.corona.service.Actions
import com.arany.corona.service.EndlessService
import com.arany.corona.service.ServiceState
import com.arany.corona.service.getServiceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private lateinit var binding: ActivityMainBinding
    /*@Inject lateinit var apiService: ApiService
    private var states = arrayListOf<StateAbstract.State>()
    private var districts = arrayListOf<DistrictAbstract.District>()

    private var selectedStateId: Int ?= null
    private var selectedDistrictId: Int ?= null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        /*callStateApi()
        initializeAdapter()
        initializeListeners()
        if(!PreferenceHelper.getDistrictId().isNullOrEmpty()){
            actionOnService(Actions.START, this)
        }*/
    }

    /*private fun initializeListeners() {
        binding.proceed.setOnClickListener {
            if(selectedDistrictId != null && selectedStateId != null){
                PreferenceHelper.setDistrictId(selectedDistrictId?.toString().toString())
                PreferenceHelper.setStateId(selectedStateId?.toString().toString())
                actionOnService(Actions.START, this)
            } else toast("Please select select and district")
        }
    }

    private fun initializeAdapter() {

    }

    private fun callStateApi() {
        CoroutineScope(coroutineContext).launch {
            binding.progressBar.show()
            val fetchedStates = withContext(Dispatchers.IO){ getStates() }
            binding.progressBar.invisible()
            states.addAll(fetchedStates)
            setStateAdapter()
        }
    }

    private fun setStateAdapter(){
        val adapter = StateAdapter(binding.stateSpinner)
        binding.stateSpinner.setSpinnerAdapter(adapter)
        binding.stateSpinner.setItems(states)
        binding.stateSpinner.lifecycleOwner = this
        binding.stateSpinner.setOnSpinnerItemSelectedListener<StateAbstract.State> { oldIndex, oldItem, newIndex, newItem ->
            selectedStateId = newItem.stateId
            callDistrictApi(selectedStateId!!)
        }
    }

    private fun callDistrictApi(stateId: Int) {
        CoroutineScope(coroutineContext).launch {
            binding.progressBar.show()
            val fetchedDistricts = withContext(Dispatchers.IO){ getDistricts(stateId) }
            binding.progressBar.invisible()
            districts.addAll(fetchedDistricts)
            setDistrictAdapter()
        }
    }

    private fun setDistrictAdapter() {
        val adapter = DistrictAdapter(binding.districtSpinner)
        binding.districtSpinner.setSpinnerAdapter(adapter)
        binding.districtSpinner.setItems(districts)
        binding.districtSpinner.lifecycleOwner = this
        binding.districtSpinner.setOnSpinnerItemSelectedListener<DistrictAbstract.District> { oldIndex, oldItem, newIndex, newItem ->
            selectedDistrictId = newItem.districtId
        }
    }

    private suspend fun getStates(): ArrayList<StateAbstract.State>{
        val states = ArrayList<StateAbstract.State>()
        val stateAbstractResponse = apiService.getStates()
        var stateAbstract: StateAbstract?= null
        if(stateAbstractResponse.isSuccessful){
            stateAbstract = stateAbstractResponse.body()
        }
        states.addAll(stateAbstract?.states?: arrayListOf())
        return states
    }

    private suspend fun getDistricts(stateId: Int): ArrayList<DistrictAbstract.District>{
        val districts = ArrayList<DistrictAbstract.District>()
        val districtAbstractResponse = apiService.getDistricts(stateId.toString())
        var districtAbstract: DistrictAbstract?= null
        if(districtAbstractResponse.isSuccessful){
            districtAbstract = districtAbstractResponse.body()
        }
        districts.addAll(districtAbstract?.districts?: arrayListOf())
        return districts
    }

    private fun actionOnService(action: Actions, context: Context) {
        if (getServiceState(context) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(context, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Timber.e("Starting the service in >=26 Mode")
                context.startForegroundService(it)
                return
            }
            Timber.e("Starting the service in < 26 Mode")
            context.startService(it)
        }
    }*/
}