package nl.giorgos.appie.country_news_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import nl.giorgos.appie.news_detail.Article;

public interface CountryNewsListContract {
    interface View {
        void updateList(CountryNewsAdapter articlesAdapter);

        void openDetailsActivity(Article article);

        void hideRefreshLayout();

        void showRefreshLayout();
    }

    interface Presenter {
        void onCreate(@NonNull final Context context);

        void onArticleClick(@NonNull Article article);

        void onRefresh();
    }
}
