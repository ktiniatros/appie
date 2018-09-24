package nl.giorgos.appie.inject;

import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.giorgos.appie.NetworkReceiver;
import nl.giorgos.appie.NewsFetcher;

import static org.mockito.Mockito.mock;

@Module
public class MockNetworkModule {
    @Provides
    @Singleton
    @NonNull
    NewsFetcher providesNewsFetcher() {
        return mock(NewsFetcher.class);
    }

    @Provides
    @Singleton
    @NonNull
    NetworkReceiver providesNetworkReceiver() {
        return mock(NetworkReceiver.class);
    }

    @Provides
    @Singleton
    @NonNull
    Picasso providesPicasso() {
        return mock(Picasso.class);
    }
}
