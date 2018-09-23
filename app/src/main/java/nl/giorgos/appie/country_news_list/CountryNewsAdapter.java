package nl.giorgos.appie.country_news_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.giorgos.appie.R;
import nl.giorgos.appie.news_detail.Article;


public class CountryNewsAdapter extends RecyclerView.Adapter<CountryNewsViewHolder> {
    private final CountryNewsListContract.Presenter presenter;

    private final List<Article> articles;

    public CountryNewsAdapter(CountryNewsListContract.Presenter presenter, List<Article> articles) {
        this.presenter = presenter;
        this.articles = articles;
    }

    @NonNull
    @Override
    public CountryNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_news_item, parent, false);
        return new CountryNewsViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryNewsViewHolder holder, int position) {
        holder.setArticle(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
