package nl.giorgos.appie.country_news_list;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import nl.giorgos.appie.NewsFetcher;
import nl.giorgos.appie.news_detail.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nl.giorgos.appie.NewsRequestHelper.HEADLINES_PATH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CountryNewsPresenterTest {
    @Mock
    private NewsFetcher newsFetcher;

    @Mock
    private CountryNewsListContract.View view;

    private String countryCode = "nl";

    private String apiKey = "apiKey";

    private Article validArticle = new Article("author", "title", "url", "urlToImage", "content");

    private CountryNewsPresenter countryNewsPresenter;

    @Mock
    private Call<CountryNews> countryNewsCall;

    @Captor
    private ArgumentCaptor<CountryNewsAdapter> countryNewsAdapterArgumentCaptor;

    @Before
    public void setUp() {
        initMocks(this);
        countryNewsPresenter = new CountryNewsPresenter(view, newsFetcher, countryCode, apiKey);
        when(newsFetcher.news(anyString(), anyString(), anyString())).thenReturn(countryNewsCall);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Callback<CountryNews> countryNewsCallback = invocation.getArgumentAt(0, Callback.class);
                countryNewsCallback.onResponse(countryNewsCall, Response.success(new CountryNews(Arrays.asList(validArticle, validArticle))));
                return null;
            }
        }).when(countryNewsCall).enqueue(any(Callback.class));
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onArticleClick_withNonNullArticle_opensDetailActivity() {
        // act
        countryNewsPresenter.onArticleClick(validArticle);

        // assert
        verify(view, times(1)).openDetailsActivity(validArticle);
    }

    @Test
    public void onRefresh_showsRefreshLayout() {
        // act
        countryNewsPresenter.onRefresh();

        // assert
        verify(view, times(1)).showRefreshLayout();
    }

    @Test
    public void onRefresh_successfulCallToNewsApi_hidesRefreshLayout() {
        // act
        countryNewsPresenter.onRefresh();

        // assert
        verify(view, times(1)).hideRefreshLayout();
    }

    @Test
    public void onRefresh_unsuccessfulCallToNewsApi_hidesRefreshLayout() {
        // arrange
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Callback<CountryNews> countryNewsCallback = invocation.getArgumentAt(0, Callback.class);
                countryNewsCallback.onFailure(countryNewsCall, mock(Throwable.class));
                return null;
            }
        }).when(countryNewsCall).enqueue(any(Callback.class));
        
        // act
        countryNewsPresenter.onRefresh();

        // assert
        verify(view, times(1)).hideRefreshLayout();
    }

    @Test
    public void onRefresh_successfulCallToNewsApi_updatesListWithValidArticles() {
        // act
        countryNewsPresenter.onRefresh();

        // assert
        verify(view, times(1)).updateList(countryNewsAdapterArgumentCaptor.capture());
        assertEquals(2, countryNewsAdapterArgumentCaptor.getValue().getItemCount());
    }

    @Test
    public void onCreate_withNonNullContext_showsRefreshLayoutAndFetchesNews() {
        // act
        countryNewsPresenter.onCreate(mock(Context.class));

        // assert
        verify(view).showRefreshLayout();
        verify(newsFetcher).news(HEADLINES_PATH, "nl", "apiKey");
    }
}