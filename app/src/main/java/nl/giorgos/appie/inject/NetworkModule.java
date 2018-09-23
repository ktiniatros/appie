package nl.giorgos.appie.inject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.giorgos.appie.NetworkReceiver;
import nl.giorgos.appie.NewsFetcher;
import nl.giorgos.appie.NewsRequestHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    @NonNull
    NewsFetcher providesNewsFetcher() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsRequestHelper.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsFetcher.class);
    }

    @Provides
    @Singleton
    @NonNull
    NetworkReceiver providesNetworkReceiver(@NonNull @ApplicationContext Context context) {
        return new NetworkReceiver((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    @Provides
    @Singleton
    @NonNull
    Picasso providesPicasso() {
        return Picasso.get();
    }
}
