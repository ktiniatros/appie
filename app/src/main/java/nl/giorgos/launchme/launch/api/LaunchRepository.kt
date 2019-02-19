package nl.giorgos.launchme.launch.api

import javax.inject.Inject

open class LaunchRepository @Inject constructor(val launchExecutor: LaunchExecutor) {
    var currentLaunches = listOf<Launch>()

    open fun fetch(amount: Int = 10): List<Launch> {
        currentLaunches = launchExecutor.getLaunches(amount)
        return currentLaunches
    }

    open fun getLaunchesByMissionName(missionName: String): List<Launch> = currentLaunches.map {
        val missionsNameLowercased = missionName.toLowerCase()
        it.copy(missions = it.missions.filter {
            it.name.toLowerCase().contains(missionsNameLowercased)
        })
    }.filter { !it.missions.isEmpty() }

    fun findLaunchWithId(id: Int) = currentLaunches.find { launch ->
        launch.id == id && !launch.location.pads.isEmpty()
    }
}
