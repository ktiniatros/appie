package nl.giorgos.appie;

import android.app.Application;
import android.support.annotation.NonNull;

import nl.giorgos.appie.inject.AppComponent;
import nl.giorgos.appie.inject.AppModule;
import nl.giorgos.appie.inject.DaggerAppComponent;

public class AppieApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = createAppComponent();
        appComponent.inject(this);
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @NonNull
    public AppComponent getComponent() {
        return appComponent;
    }
}
