package nl.giorgos.launchme.launch.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import retrofit2.Response as RetrofitResponse

data class Response(val launches: List<Launch>, val total: Int, val offset: Int, val count: Int)
data class Launch(
    val id: Int,
    val name: String,
    val isoend: Date,
    val missions: List<Mission>,
    val location: Location
) {
    fun getMissionsName(fallbackName: String): String {
        if (missions.isEmpty()) {
            return fallbackName
        }
        val lastMissionName = missions.last().name
        var missionsName = ""
        missions.forEach { mission ->
            missionsName += mission.name
            if (mission.name != lastMissionName) {
                missionsName += " | "
            }
        }
        return missionsName
    }

    companion object {
        fun getCountDownText(fromDate: Date, toDate: Date): String {
            if (toDate <= fromDate) {
                return "00:00"
            }
            val differenceMs = toDate.time - fromDate.time
            val hours = TimeUnit.MILLISECONDS.toHours(differenceMs).toInt() % 24
            val minutes = TimeUnit.MILLISECONDS.toMinutes(differenceMs).toInt() % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(differenceMs).toInt() % 60
            return when {
                hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
                minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
                seconds > 0 -> String.format("00:%02d", seconds)
                else -> {
                    "00:00"
                }
            }
        }
    }
}

data class Mission(val id: Int, val name: String)
data class Pad(val latitude: Float, val longitude: Float)
data class Location(val id: Int, val name: String, val pads: List<Pad>)

val BaseUrl = "https://launchlibrary.net/1.4/"

interface LaunchService {
    @GET("launch/next/{amountOfLaunches}")
    fun getLaunches(@Path("amountOfLaunches") amountOfLaunches: Int): Call<Response>
}

class LaunchExecutor @Inject constructor(val launchService: LaunchService) {
    fun getLaunches(amount: Int): List<Launch> {
        var response: RetrofitResponse<Response>? = null

        try {
            response = launchService.getLaunches(amount).execute()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return response?.body()?.launches ?: listOf()
    }
}
