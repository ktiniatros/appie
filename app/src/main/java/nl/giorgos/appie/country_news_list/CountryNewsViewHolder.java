package nl.giorgos.appie.country_news_list;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.giorgos.appie.R;
import nl.giorgos.appie.news_detail.Article;

public class CountryNewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.article_title_text_view)
    TextView articleTitleTextView;

    private CountryNewsListContract.Presenter presenter;

    private Article article;

    public CountryNewsViewHolder(View itemView, CountryNewsListContract.Presenter presenter) {
        super(itemView);
        this.presenter = presenter;
        ButterKnife.bind(this, itemView);
    }

    public void setArticle(@NonNull Article article) {
        this.article = article;
        articleTitleTextView.setText(article.getTitle());
    }

    @OnClick(R.id.article_title_text_view)
    public void onClick(View view) {
        presenter.onArticleClick(article);
    }
}
