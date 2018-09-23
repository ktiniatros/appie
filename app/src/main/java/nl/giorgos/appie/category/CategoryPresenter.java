package nl.giorgos.appie.category;


import android.support.annotation.NonNull;

import nl.giorgos.appie.country_news_list.CountryNewsListActivity;

public class CategoryPresenter implements CategoryContract.Presenter {
    private final CategoryContract.View view;

    public CategoryPresenter(CategoryContract.View view) {
        this.view = view;
    }

    @Override
    public void onCountrySelected(@NonNull String countryCode) {
        view.openNewsActivity(countryCode);
    }
}
