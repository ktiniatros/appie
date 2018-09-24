package nl.giorgos.appie.base;

import android.support.annotation.NonNull;

import nl.giorgos.appie.NetworkReceiver;
import nl.giorgos.appie.R;

public class BasePresenter implements BaseContract.Presenter {
    private BaseContract.View view;

    public BasePresenter(BaseContract.View view) {
        this.view = view;
    }

    @Override
    public void connectivityChanged(boolean isOnline) {
        if (isOnline) {
            view.hideSnackbar();
        } else {
            view.showSnackbarWithTextId(R.string.offline);
        }
    }

    @Override
    public void onCreate(@NonNull NetworkReceiver networkReceiver) {
        networkReceiver.addListener(this);
    }
}
