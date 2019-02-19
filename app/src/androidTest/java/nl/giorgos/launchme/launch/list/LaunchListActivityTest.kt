package nl.giorgos.launchme.launch.list

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.whenever
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.R
import nl.giorgos.launchme.inject.DaggerTestAppComponent
import nl.giorgos.launchme.inject.TestAppComponent
import nl.giorgos.launchme.inject.TestModules
import nl.giorgos.launchme.launch.api.Launch
import nl.giorgos.launchme.launch.api.LaunchRepository
import nl.giorgos.launchme.launch.api.Location
import nl.giorgos.launchme.launch.api.Mission
import nl.giorgos.launchme.launch.detail.DetailActivity
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import java.util.Date
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class LaunchListActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(LaunchListActivity::class.java, false, false)

    private lateinit var testAppComponent: TestAppComponent

    @Inject
    lateinit var launchRepository: LaunchRepository

    @Before
    fun setUp() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as LaunchApplication
        testAppComponent = DaggerTestAppComponent.builder().modules(TestModules(app)).build()
        app.appComponent = testAppComponent
        testAppComponent.inject(this)
    }

    @Test
    fun launch_dataAreLoaded_showsListWithData() {
        // setup
        whenever(launchRepository.fetch()).thenReturn(getTwoLaunchItems())

        // act
        activityRule.launchActivity(null)

        // assert
        onView(withId(R.id.recyclerView)).check(matches(isCompletelyDisplayed()))
        onView(withText("mission1")).check(matches(isCompletelyDisplayed()))
        onView(withText("mission2")).check(matches(isCompletelyDisplayed()))
        onView(withText("launch1")).check(matches(isCompletelyDisplayed()))
        onView(withText("launch2")).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun launch_dataAreLoadedAndFiltered_showsListWithFilteredDataOnly() {
        // setup
        launchRepository.currentLaunches = getTwoLaunchItems()
        whenever(launchRepository.fetch()).thenReturn(launchRepository.currentLaunches)
        whenever(launchRepository.getLaunchesByMissionName(ArgumentMatchers.anyString())).thenCallRealMethod()

        // act
        activityRule.launchActivity(null)
        onView(withId(nl.giorgos.launchme.R.id.search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("mission2"))

        // assert
        onView(withId(nl.giorgos.launchme.R.id.recyclerView)).check(matches(isCompletelyDisplayed()))
        onView(withText("mission1")).check(doesNotExist())
        onView(allOf(withText("mission2"), withId(R.id.rightTextView))).check(
            matches(
                isCompletelyDisplayed()
            )
        )
        onView(withText("launch1")).check(doesNotExist())
        onView(withText("launch2")).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun clickOnRow_dataAreLoaded_opensDetailActivity() {
        // setup
        Intents.init()
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        whenever(launchRepository.fetch()).thenReturn(getTwoLaunchItems())
        Intents.intending(IntentMatchers.hasComponent(DetailActivity::class.java.name))
            .respondWith(result)
        activityRule.launchActivity(null)

        // act
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        // assert
        Intents.intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
    }

    private fun getTwoLaunchItems(): List<Launch> {
        val mission1 = Mission(1, "mission1")
        val mission2 = Mission(2, "mission2")

        val launch1 = Launch(
            1,
            "launch1",
            Date(Date().time + 3_600_600),
            listOf(mission1),
            Location(1, "location1", emptyList())
        )
        val launch2 = Launch(
            1,
            "launch2",
            Date(Date().time + 3_600_600),
            listOf(mission2),
            Location(1, "location2", emptyList())
        )

        return listOf(launch1, launch2)
    }
}