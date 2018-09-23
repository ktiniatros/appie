package nl.giorgos.appie.news_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import nl.giorgos.appie.R;
import nl.giorgos.appie.base.BaseActivity;

public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {
    public static final String ARTICLE_EXTRA = "article_extra";

    @Inject
    Picasso picasso;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.content_text_view)
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_detail);
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        final Article article = getIntent().getParcelableExtra(ARTICLE_EXTRA);
        NewsDetailContract.Presenter presenter = new NewsDetailPresenter(article, this);
    }

    @Override
    public void updateTitle(@NonNull String title) {
        titleTextView.setText(title);
    }

    @Override
    public void updateImage(@NonNull String image) {
        picasso.load(image).into(imageView);
    }

    @Override
    public void updateContent(@NonNull String content) {
        contentTextView.setText(content);
    }
}
