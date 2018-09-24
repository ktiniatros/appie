package nl.giorgos.appie;

import nl.giorgos.appie.inject.AppComponent;
import nl.giorgos.appie.inject.AppModule;
import nl.giorgos.appie.inject.DaggerTestAppComponent;
import nl.giorgos.appie.inject.TestAppComponent;

public class MockApp extends AppieApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ((TestAppComponent) getComponent()).inject(this);
    }

    @Override
    protected AppComponent createAppComponent() {
        return DaggerTestAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }
}
