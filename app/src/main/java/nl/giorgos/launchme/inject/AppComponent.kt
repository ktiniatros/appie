package nl.giorgos.launchme.inject

import android.app.Application
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import nl.giorgos.launchme.launch.api.BaseUrl
import nl.giorgos.launchme.launch.api.LaunchExecutor
import nl.giorgos.launchme.launch.api.LaunchRepository
import nl.giorgos.launchme.launch.api.LaunchService
import nl.giorgos.launchme.launch.detail.DetailViewModel
import nl.giorgos.launchme.launch.list.LaunchListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class Modules(private val application: Application) {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        //20190222T014500Z
        val gson = GsonBuilder()
            .setDateFormat("yyyyMMdd'T'HHmmss")
            .create()
        return Retrofit.Builder().baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun providesLaunchService(retrofit: Retrofit): LaunchService {
        return retrofit.create(LaunchService::class.java)
    }

    @Provides
    @Singleton
    fun providesLaunchRepository(launchExecutor: LaunchExecutor): LaunchRepository {
        return LaunchRepository(launchExecutor)
    }
}

@Singleton
@Component(modules = arrayOf(Modules::class))
interface AppComponent {
    fun inject(launchListViewModel: LaunchListViewModel)

    fun inject(detailViewModel: DetailViewModel)
}