package nl.giorgos.launchme.launch.api

import javax.inject.Inject

class LaunchRepository @Inject constructor(val launchExecutor: LaunchExecutor) {
    var currentLaunches = listOf<Launch>()

    fun fetch(amount: Int = 10): List<Launch> {
        currentLaunches = launchExecutor.getLaunches(amount)
        return currentLaunches
    }

    fun getLaunchesByMissionName(missionName: String): List<Launch> = currentLaunches.map {
        it.copy(missions = it.missions.filter {
            it.name.contains(missionName)
        })
    }.filter { !it.missions.isEmpty() }

    fun findLaunchWithId(id: Int) = currentLaunches.find { launch ->
        launch.id == id && !launch.location.pads.isEmpty()
    }
}
