package nl.giorgos.appie.base;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import nl.giorgos.appie.NetworkReceiver;
import nl.giorgos.appie.R;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class BasePresenterTest {
    @Mock
    private BaseContract.View view;

    private BasePresenter basePresenter;

    @Before
    public void setUp() {
        initMocks(this);
        basePresenter = new BasePresenter(view);
    }

    @Test
    public void connectivityChanged_online_hidesSnackBar() {
        // act
        basePresenter.connectivityChanged(true);

        // assert
        verify(view, times(1)).hideSnackbar();
    }

    @Test
    public void connectivityChanged_offline_showsSnackBar() {
        // act
        basePresenter.connectivityChanged(false);

        // assert
        verify(view, times(1)).showSnackbarWithTextId(R.string.offline);
    }

    @Test
    public void onCreate_withNotNullNetworkReceiver_addsPresenterAsListener() {
        // arrange
        final NetworkReceiver networkReceiver = mock(NetworkReceiver.class);

        // act
        basePresenter.onCreate(networkReceiver);

        // assert
        verify(networkReceiver, times(1)).addListener(basePresenter);
    }
}