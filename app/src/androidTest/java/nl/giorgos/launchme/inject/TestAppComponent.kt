package nl.giorgos.launchme.inject

import android.app.Application
import dagger.Component
import nl.giorgos.launchme.launch.api.LaunchExecutor
import nl.giorgos.launchme.launch.api.LaunchRepository
import nl.giorgos.launchme.launch.api.LaunchService
import nl.giorgos.launchme.launch.list.LaunchListActivityTest
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import javax.inject.Singleton

class TestModules(private val application: Application): Modules(application) {
    override fun providesLaunchService(retrofit: Retrofit) = mock(LaunchService::class.java)

    override fun providesLaunchRepository(launchExecutor: LaunchExecutor) = mock(LaunchRepository::class.java)

}

@Singleton
@Component(modules = arrayOf(Modules::class))
interface TestAppComponent : AppComponent {
    fun inject(launchListActivityTest: LaunchListActivityTest)
}