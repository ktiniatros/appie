package nl.giorgos.launchme.launch.api

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

/**
 * All unit test names follow the convention methodUnderText_stateUnderTest_expectedResult
 */
class LaunchTest {

    @Test
    fun getMissionsName_noMissions_returnsDefaultText() {
        // setup
        val missions = listOf<Mission>()
        val launch = Launch(1, "name", Date(), missions, mockk())

        // act
        val missionsName = launch.getMissionsName("no missions")

        // assert
        assertEquals("no missions", missionsName)
    }

    @Test
    fun getMissionsName_twoMissions_returnsCorrectString() {
        // setup
        val mission1 = mockk<Mission>()
        val mission2 = mockk<Mission>()
        every { mission1.name } returns "mission1"
        every { mission2.name } returns "mission2"
        val launch = Launch(2, "name2", Date(), listOf(mission1, mission2), mockk())

        // act
        val missionsName = launch.getMissionsName("no missions")

        // assert
        assertEquals("mission1 | mission2", missionsName)
    }

    @Test
    fun getCountDownText_timePassed_returnsZeros() {
        // setup
        val fromDate = Date()
        val toDate = Date(1)

        // act
        val countDownText = Launch.getCountDownText(fromDate, toDate)

        // assert
        assertEquals("00:00", countDownText)
    }

    @Test
    fun getCountDownText_fromTwoDatesWithOneSecondDifference_returnsFormattedText() {
        // setup
        val fromDate = Date(1)
        val toDate = Date(1002)

        // act
        val countDownText = Launch.getCountDownText(fromDate, toDate)

        // assert
        assertEquals("00:01", countDownText)
    }

    @Test
    fun getCountDownText_fromTwoDatesWithOneMinuteDifference_returnsFormattedText() {
        // setup
        val fromDate = Date(1)
        val toDate = Date(60_001)

        // act
        val countDownText = Launch.getCountDownText(fromDate, toDate)

        // assert
        assertEquals("01:00", countDownText)
    }

    @Test
    fun getCountDownText_fromTwoDatesWithOneHourDifference_returnsFormattedText() {
        // setup
        val fromDate = Date(1)
        val toDate = Date(3_600_001)

        // act
        val countDownText = Launch.getCountDownText(fromDate, toDate)

        // assert
        assertEquals("01:00:00", countDownText)
    }

    @Test
    fun getCountDownText_fromTwoDatesWithrDifference_returnsFormattedText() {
        // setup
        val fromDate = Date(1)
        val toDate = Date(3_961_001)

        // act
        val countDownText = Launch.getCountDownText(fromDate, toDate)

        // assert
        assertEquals("01:06:01", countDownText)
    }
}