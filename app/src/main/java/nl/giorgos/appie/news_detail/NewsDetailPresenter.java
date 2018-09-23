package nl.giorgos.appie.news_detail;


public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private final Article article;

    private final NewsDetailContract.View view;

    public NewsDetailPresenter(Article article, NewsDetailContract.View view) {
        this.article = article;
        this.view = view;

        setupView();
    }

    private void setupView() {
        view.updateTitle(article.getTitle());
        view.updateImage(article.getUrlToImage());
        view.updateContent(article.getContent());
    }
}
