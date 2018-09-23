package nl.giorgos.appie.base;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.giorgos.appie.AppieApp;
import nl.giorgos.appie.NetworkReceiver;
import nl.giorgos.appie.inject.AppComponent;

public abstract class BaseActivity extends Activity implements BaseContract.View{

    @Inject
    NetworkReceiver networkReceiver;

    private Unbinder unbinder;

    private BasePresenter basePresenter;

    private Snackbar snackBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unbinder = ButterKnife.bind(this);

        getAppComponent().inject(this);

        basePresenter = new BasePresenter(this);
        basePresenter.onCreate(networkReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    protected AppComponent getAppComponent() {
        return ((AppieApp) getApplication()).getComponent();
    }

    @Override
    public void openActivity(@NonNull Class cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    public void showSnackbarWithTextId(int stringId) {
        snackBar = Snackbar.make(findViewById(android.R.id.content), getString(stringId), Snackbar.LENGTH_INDEFINITE);
        snackBar.show();
    }

    @Override
    public void hideSnackbar() {
        if (snackBar != null) {
            snackBar.dismiss();
        }
    }
}
