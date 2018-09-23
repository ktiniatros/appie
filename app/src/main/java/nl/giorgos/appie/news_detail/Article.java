package nl.giorgos.appie.news_detail;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    private String author;
    private String title;
    private String url;
    private String urlToImage;
    private String content;

    public Article(String author, String title, String url, String urlToImage, String content) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getContent() {
        return content;
    }

    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(content);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
