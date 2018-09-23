package nl.giorgos.appie.country_news_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import nl.giorgos.appie.NewsFetcher;
import nl.giorgos.appie.R;
import nl.giorgos.appie.base.BaseActivity;
import nl.giorgos.appie.news_detail.Article;
import nl.giorgos.appie.news_detail.NewsDetailActivity;

public class CountryNewsListActivity extends BaseActivity implements CountryNewsListContract.View {
    public static final String COUNTRY_CODE_EXTRA = "country_code_extra";

    @Inject
    NewsFetcher newsFetcher;

    @BindView(R.id.articles_list)
    RecyclerView articlesList;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_country_news_list);
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        articlesList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        CountryNewsPresenter presenter = new CountryNewsPresenter(this, newsFetcher, getIntent().getStringExtra(COUNTRY_CODE_EXTRA), getString(R.string.news_api_key));
        presenter.onCreate(this);
        swipeRefreshLayout.setOnRefreshListener(presenter);
    }

    @Override
    public void updateList(CountryNewsAdapter countryNewsAdapter) {
        articlesList.setAdapter(countryNewsAdapter);
    }

    @Override
    public void openDetailsActivity(Article article) {
        final Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.ARTICLE_EXTRA, article);
        startActivity(intent);
    }

    @Override
    public void hideRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showRefreshLayout() {
        swipeRefreshLayout.setRefreshing(true);
    }
}
