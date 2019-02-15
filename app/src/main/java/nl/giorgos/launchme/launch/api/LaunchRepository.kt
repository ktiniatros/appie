package nl.giorgos.launchme.launch.api

import javax.inject.Inject

class LaunchRepository @Inject constructor(val launchService: LaunchService) {
    fun fetch(amount: Int = 10): List<Launch> {
        return launchService.getLaunches(amount).execute().body()?.launches ?: ArrayList()
    }
}
