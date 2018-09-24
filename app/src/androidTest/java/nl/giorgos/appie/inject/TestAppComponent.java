package nl.giorgos.appie.inject;

import javax.inject.Singleton;

import dagger.Component;
import nl.giorgos.appie.MockApp;
import nl.giorgos.appie.category_news_list.CountryNewsListActivityTest;
import nl.giorgos.appie.news_detail.NewsDetailActivityTest;

@Singleton
@Component(modules = {
    AppModule.class,
    MockNetworkModule.class
})
public interface TestAppComponent extends AppComponent {
    void inject(MockApp mockApp);

    void inject(CountryNewsListActivityTest countryNewsListActivityTest);

    void inject(NewsDetailActivityTest newsDetailActivityTest);
}
