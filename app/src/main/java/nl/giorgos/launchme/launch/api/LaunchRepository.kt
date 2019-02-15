package nl.giorgos.launchme.launch.api

import javax.inject.Inject

class LaunchRepository @Inject constructor(val launchService: LaunchService) {
    fun fetch(offset: Int = 0): List<Launch> {
        return launchService.getLaunches().execute().body()?.launches ?: ArrayList()
    }
}