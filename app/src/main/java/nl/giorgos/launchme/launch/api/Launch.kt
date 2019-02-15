package nl.giorgos.launchme.launch.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Date
import javax.inject.Inject

data class Response(val launches: List<Launch>, val total: Int, val offset: Int, val count: Int)
data class Launch(val id: Int, val name: String, val isoend: Date, val missions: List<Mission>, val location: Location)
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
        return launchService.getLaunches(amount).execute().body()?.launches ?: listOf()
    }
}
