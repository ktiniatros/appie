package nl.giorgos.appie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NetworkReceiver extends BroadcastReceiver {

    private final ConnectivityManager connectivityManager;

    private List<NetworkListener> listeners = new ArrayList<>();

    public NetworkReceiver(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        for (NetworkListener networkListener : listeners) {
            networkListener.connectivityChanged(isConnected());
        }
    }

    public void addListener(@NonNull  NetworkListener networkListener) {
        if (!listeners.contains(networkListener)) {
            listeners.add(networkListener);
            networkListener.connectivityChanged(isConnected());
        }
    }

    private boolean isConnected() {
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public interface NetworkListener {
        void connectivityChanged(boolean isOnline);
    }
}
