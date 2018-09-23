package nl.giorgos.appie.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import nl.giorgos.appie.NetworkReceiver;

public interface BaseContract {
    interface View {
        void openActivity(@NonNull Class cls);

        void showSnackbarWithTextId(@StringRes int stringId);

        void hideSnackbar();
    }

    interface Presenter extends NetworkReceiver.NetworkListener {
        void connectivityChanged(boolean isOnline);

        void onCreate(@NonNull NetworkReceiver networkReceiver);
    }
}
