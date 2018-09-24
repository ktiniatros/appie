package nl.giorgos.appie.category_news_list;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import javax.inject.Inject;

import nl.giorgos.appie.MockApp;
import nl.giorgos.appie.MockRule;
import nl.giorgos.appie.NewsFetcher;
import nl.giorgos.appie.R;
import nl.giorgos.appie.country_news_list.CountryNews;
import nl.giorgos.appie.country_news_list.CountryNewsListActivity;
import nl.giorgos.appie.inject.TestAppComponent;
import nl.giorgos.appie.news_detail.Article;
import nl.giorgos.appie.news_detail.NewsDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static nl.giorgos.appie.country_news_list.CountryNewsListActivity.COUNTRY_CODE_EXTRA;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(AndroidJUnit4.class)
public class CountryNewsListActivityTest {
    @Inject
    NewsFetcher newsFetcher;

    @Mock
    private Call<CountryNews> countryNewsCall;

    private Article validArticle = new Article("author", "title", "url", "urlToImage", "content");

    @Rule
    public ActivityTestRule<CountryNewsListActivity> activityTestRule = new MockRule<>(CountryNewsListActivity.class);

    @Before
    public void setUp() {
        getTestAppComponent().inject(this);
        initMocks(this);
        when(newsFetcher.news(anyString(), anyString(), anyString())).thenReturn(countryNewsCall);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Callback<CountryNews> countryNewsCallback = invocation.getArgument(0);
                countryNewsCallback.onResponse(countryNewsCall, Response.success(new CountryNews(Arrays.asList(validArticle, validArticle))));
                return null;
            }
        }).when(countryNewsCall).enqueue(any(Callback.class));
        Intents.release();
    }

    @Test
    public void clickOnArticle_ofCountryNewsListActivityWithDutchArticles_opensNewsDetailActivity() {
        // arrange
        Intents.init();
        final String componentName = NewsDetailActivity.class.getName();
        Intents.intending(hasComponent(componentName))
            .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
        Intent startingIntent = new Intent();
        startingIntent.putExtra(COUNTRY_CODE_EXTRA, "nl");
        activityTestRule.launchActivity(startingIntent);

        // act
        onView(withId(R.id.articles_list))
            .perform(actionOnItemAtPosition(0, click()));

        // assert
        Intents.intended(hasComponent(componentName));
        Intents.intended(hasExtra(NewsDetailActivity.ARTICLE_EXTRA, validArticle));
    }

    private TestAppComponent getTestAppComponent() {
        MockApp mockApp = (MockApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
        return (TestAppComponent) mockApp.getComponent();
    }
}
