package nl.giorgos.appie.country_news_list;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import nl.giorgos.appie.news_detail.Article;

public class CountryNews implements Parcelable {
    List<Article> articles;

    public CountryNews(List<Article> articles) {
        this.articles = articles;
    }

    protected CountryNews(Parcel in) {
        if (in.readByte() == 0x01) {
            articles = new ArrayList<Article>();
            in.readList(articles, Article.class.getClassLoader());
        } else {
            articles = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (articles == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(articles);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CountryNews> CREATOR = new Parcelable.Creator<CountryNews>() {
        @Override
        public CountryNews createFromParcel(Parcel in) {
            return new CountryNews(in);
        }

        @Override
        public CountryNews[] newArray(int size) {
            return new CountryNews[size];
        }
    };
}
