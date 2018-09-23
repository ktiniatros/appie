package nl.giorgos.appie.country_news_list;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import nl.giorgos.appie.NewsFetcher;
import nl.giorgos.appie.R;
import nl.giorgos.appie.news_detail.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nl.giorgos.appie.NewsRequestHelper.HEADLINES_PATH;

public class CountryNewsPresenter implements CountryNewsListContract.Presenter, SwipeRefreshLayout.OnRefreshListener {
    private final NewsFetcher newsFetcher;

    private final String countryCode;

    private final CountryNewsListContract.View view;

    private final String apiKey;

    public CountryNewsPresenter(@NonNull CountryNewsListContract.View view, @NonNull NewsFetcher newsFetcher, @NonNull String countryCode, @NonNull String apiKey) {
        this.newsFetcher = newsFetcher;
        this.countryCode = countryCode;
        this.view = view;
        this.apiKey = apiKey;
    }

    @Override
    public void onCreate(@NonNull final Context context) {
        onRefresh();
    }

    private boolean isArticleValid(@Nullable Article article) {
        return article != null &&
                article.getTitle() != null &&
                article.getAuthor() != null &&
                article.getContent() != null &&
                article.getUrl() != null &&
                article.getUrlToImage() != null;
    }

    @Override
    public void onArticleClick(@NonNull Article article) {
        view.openDetailsActivity(article);
    }

    @Override
    public void onRefresh() {
        view.showRefreshLayout();
        newsFetcher.news(HEADLINES_PATH, countryCode, apiKey).enqueue(new Callback<CountryNews>() {
            @Override
            public void onResponse(@NonNull Call<CountryNews> call, @NonNull Response<CountryNews> response) {
                view.hideRefreshLayout();
                CountryNews countryNews = response.body();
                List<Article> validArticles = new ArrayList<>();
                if (countryNews != null && countryNews.articles != null) {
                    for (Article article : countryNews.articles) {
                        if (isArticleValid(article)) {
                            validArticles.add(article);
                        }
                    }
                }
                view.updateList(new CountryNewsAdapter(CountryNewsPresenter.this, validArticles));
            }

            @Override
            public void onFailure(@NonNull Call<CountryNews> call, @NonNull Throwable t) {
                view.hideRefreshLayout();
                t.printStackTrace();
            }
        });
    }
}
