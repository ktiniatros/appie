package nl.giorgos.launchme.launch.api

import retrofit2.Call
import retrofit2.http.GET

data class Response(val launches: List<Launch>, val total: Int, val offset: Int, val count: Int)
data class Launch(val id: Int, val name: String)

val BaseUrl = "https://launchlibrary.net/1.4/"

interface Api {
    @GET("launch")
    fun getLaunches(): Call<Response>
}