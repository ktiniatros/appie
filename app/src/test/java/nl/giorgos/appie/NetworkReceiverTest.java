package nl.giorgos.appie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class NetworkReceiverTest {
    @Mock
    private Context context;

    @Mock
    private Intent intent;

    @Mock
    private ConnectivityManager connectivityManager;

    @Mock
    private NetworkInfo networkInfo;

    @Mock
    private NetworkReceiver.NetworkListener networkListener;

    private NetworkReceiver networkReceiver;

    @Before
    public void setUp() {
        initMocks(this);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnected()).thenReturn(true);
    }

    @Test
    public void onReceive_listenerAdded_isConnected_listenerConnectivityChangedIsCalledTwice() throws Exception {
        // arrange
        networkReceiver = new NetworkReceiver(connectivityManager);
        networkReceiver.addListener(networkListener);

        // act
        networkReceiver.onReceive(context, intent);

        // assert
        verify(networkListener, times(2)).connectivityChanged(true);
    }

    @Test
    public void onReceive_listenerAdded_isNotConnected_listenerConnectivityChanged() throws Exception {
        // arrange
        networkReceiver = new NetworkReceiver(connectivityManager);
        networkReceiver.addListener(networkListener);
        when(networkInfo.isConnected()).thenReturn(false);

        // act
        networkReceiver.onReceive(context, intent);

        // assert
        verify(networkListener, times(1)).connectivityChanged(false);
    }

    @Test
    public void addListener_addListenerTwice_listenerIsNotifiedOnce() throws Exception {
        // arrange
        networkReceiver = new NetworkReceiver(connectivityManager);
        networkReceiver.addListener(networkListener);

        // act
        networkReceiver.addListener(networkListener);

        // assert
        verify(networkListener, times(1)).connectivityChanged(true);
    }

}