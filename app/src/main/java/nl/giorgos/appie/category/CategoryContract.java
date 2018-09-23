package nl.giorgos.appie.category;

import android.support.annotation.NonNull;

import nl.giorgos.appie.base.BaseContract;

public interface CategoryContract {
    interface View extends BaseContract.View {
        void openNewsActivity(@NonNull String countryCode);
    }

    interface Presenter {
        void onCountrySelected(@NonNull String countryCode);
    }
}
