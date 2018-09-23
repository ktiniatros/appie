package nl.giorgos.appie.news_detail;

import android.support.annotation.NonNull;

public interface NewsDetailContract {
    interface View {
        void updateTitle(@NonNull String title);

        void updateImage(@NonNull String image);

        void updateContent(@NonNull String content);
    }

    interface Presenter {

    }
}
