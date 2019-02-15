package nl.giorgos.launchme.launch.api

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date
import retrofit2.Response as RetrofitResponse

class LaunchRepositoryTest {
    val launchExecutor = mockk<LaunchExecutor>(relaxed = true)
    val launchRepository = LaunchRepository(launchExecutor)

    @Before
    fun setUp() {
        every { launchExecutor.getLaunches(10) } returns getSomeLaunches()
    }

    @Test
    fun getLaunchesByMissionName_withFetchedLaunches_returnsTwoMissionsWithName2() {
        // setup
        launchRepository.fetch()

        // act
        val launches = launchRepository.getLaunchesByMissionName("missionName2")

        // assert
        assertEquals(2, launches.size)
    }

    @Test
    fun getLaunchesByMissionName_withFetchedLaunches_returnsOneMissionWithNameLaunch1() {
        // setup
        val l = launchRepository.fetch()

        // act
        val launches = launchRepository.getLaunchesByMissionName("Launch1")

        // assert
        assertEquals(1, launches.size)
    }

    @Test
    fun fetch_twoLaunches_returnsTwoLaunches() {
        // act
        val launches = launchRepository.fetch()

        // assert
        assertEquals(2, launches.size)
    }

    private fun getSomeLaunches(): List<Launch> {
        val launches = mutableListOf<Launch>()

        launches.add(
            Launch(
                1,
                "launch1",
                Date(),
                getSomeMissions("1"),
                Location(1, "name", listOf())
            )
        )
        launches.add(
            Launch(
                2,
                "launch2",
                Date(),
                getSomeMissions("2"),
                Location(1, "name", listOf())
            )
        )

        return launches
    }

    private fun getSomeMissions(launchName: String): List<Mission> {
        val missions = mutableListOf<Mission>()
        val amountOfMissions = 3

        for (i in 1..amountOfMissions) {
            missions.add(Mission(i, "Launch$launchName - missionName$i"))
        }


        return missions
    }
}