package com.example.interview.data.remote

import android.os.Looper
import android.os.NetworkOnMainThreadException
import com.example.interview.data.FlightStatusDto
import io.reactivex.Single
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class FlightStatusServiceImpl : FlightStatusService {

    private val baseResponse = listOf(
        FlightStatusDto("Albuquerque, New Mexico, USA (ABQ)"),
        FlightStatusDto("Nantucket, Massachusetts, USA (ACK)"),
        FlightStatusDto("Albany, New York, USA (ALB)"),
        FlightStatusDto("Walla Walla, Washington, USA (ALW)"),
        FlightStatusDto("Anchorage, Alaska, USA (ANC)"),
        FlightStatusDto("Atlanta, Georgia, USA (ATL)"),
        FlightStatusDto("Abu Dhabi, United Arab Emirates (AUH)"),
        FlightStatusDto("Austin, Texas, USA (AUS)"),
        FlightStatusDto("Hartford, Connecticut, USA (BDL)"),
        FlightStatusDto("Bridgetown, Barbados (BGI)"),
        FlightStatusDto("Birmingham, Alabama, USA (BHM)"),
        FlightStatusDto("Billings, Montana, USA (BIL)"),
        FlightStatusDto("Bellingham, Washington, USA (BLI)"),
        FlightStatusDto("Nashville, Tennessee, USA (BNA)"),
        FlightStatusDto("Boise, Idaho, USA (BOI)"),
        FlightStatusDto("Boston, Massachusetts, USA (BOS)"),
        FlightStatusDto("Aguadilla, Puerto Rico (BQN)"),
        FlightStatusDto("Burlington, Vermont, USA (BTV)"),
        FlightStatusDto("Buffalo, New York, USA (BUF)"),
        FlightStatusDto("L.A. Burbank, California, USA (BUR)"),
        FlightStatusDto("Washington Baltimore, Maryland, USA (BWI)"),
        FlightStatusDto("Bozeman, Montana, USA (BZN)"),
        FlightStatusDto("Columbia, South Carolina, USA (CAE)"),
        FlightStatusDto("Akron-Canton, Ohio, USA (CAK)"),
        FlightStatusDto("Charlottesville, Virginia, USA (CHO)"),
        FlightStatusDto("Charleston, South Carolina, USA (CHS)"),
        FlightStatusDto("Cedar Rapids, Iowa, USA (CID)"),
        FlightStatusDto("Cleveland, Ohio, USA (CLE)"),
        FlightStatusDto("Charlotte, North Carolina, USA (CLT)"),
        FlightStatusDto("Columbus, Ohio, USA (CMH)"),
        FlightStatusDto("Hancock, Michigan, USA (CMX)"),
        FlightStatusDto("Cincinnati, Ohio, USA (CVG)")
    )

    private val random = Random(System.currentTimeMillis())

    override suspend fun getStatuses(fromCode: String): List<FlightStatusDto> {
        simulateNetworkDelayCoroutines()
        return performNetworkCall()
    }

    override fun getStatusesSingle(fromCode: String): Single<List<FlightStatusDto>> =
        Single.fromCallable(::performNetworkCall)
            .simulateNetworkDelayRx()

    private fun performNetworkCall(): List<FlightStatusDto> {
        ensureNotMainThread()
        return baseResponse.map { flight ->
            randomiseResponseItem(flight)
        }
    }

    private fun randomiseResponseItem(
        flight: FlightStatusDto
    ) = if (Random.nextBoolean()) {
            val randomRatio = 1.0 + random.nextDouble() / 5.0 - 0.1
            FlightStatusDto(flight.destination,
                "${String.format(Locale.ENGLISH,"%02d", Random.nextInt(0, 24))}:" +
                        String.format(Locale.ENGLISH,"%02d", Random.nextInt(0, 60))
            )
        } else flight

    private fun Single<List<FlightStatusDto>>.simulateNetworkDelayRx(): Single<List<FlightStatusDto>> =
        delay(random.nextInt(0, 1500).toLong(), TimeUnit.MILLISECONDS)

    private suspend fun simulateNetworkDelayCoroutines() {
        delay(random.nextInt(0, 1500).toLong())
    }

    private fun ensureNotMainThread() {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            throw NetworkOnMainThreadException()
        }
    }
}