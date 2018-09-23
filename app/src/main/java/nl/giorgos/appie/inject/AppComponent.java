package nl.giorgos.appie.inject;

import javax.inject.Singleton;

import dagger.Component;
import nl.giorgos.appie.AppieApp;
import nl.giorgos.appie.base.BaseActivity;
import nl.giorgos.appie.category.CategoryActivity;
import nl.giorgos.appie.country_news_list.CountryNewsListActivity;
import nl.giorgos.appie.news_detail.NewsDetailActivity;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(AppieApp app);

    void inject(BaseActivity baseActivity);

    void inject(CategoryActivity categoryActivity);

    void inject(CountryNewsListActivity countryNewsListActivity);

    void inject(NewsDetailActivity newsDetailActivity);
}
