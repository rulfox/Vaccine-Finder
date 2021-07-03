package com.arany.corona.domain.useCase

import com.anushka.newsapiclient.data.util.Resource
import com.arany.corona.data.model.StateResponse
import com.arany.corona.domain.repository.LocationRepository
import javax.inject.Inject

class GetStatesUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    suspend fun execute(): Resource<StateResponse> {
        return locationRepository.getStates()
    }
}