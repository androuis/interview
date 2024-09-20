package com.example.interview.data.remote

import com.example.interview.data.FlightStatusDto
import io.reactivex.Single

interface FlightStatusService {
    suspend fun getStatuses(fromCode: String): List<FlightStatusDto>
    fun getStatusesSingle(fromCode: String): Single<List<FlightStatusDto>>
}