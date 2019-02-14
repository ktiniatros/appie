package nl.giorgos.launchme.inject

import android.app.Application
import dagger.Component
import dagger.Module
import dagger.Provides
import nl.giorgos.launchme.launch.api.BaseUrl
import nl.giorgos.launchme.launch.list.LaunchListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class Modules(private val application: Application) {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }
}

@Singleton
@Component(modules = arrayOf(Modules::class))
interface AppComponent {
    fun inject(launchListViewModel: LaunchListViewModel)
}