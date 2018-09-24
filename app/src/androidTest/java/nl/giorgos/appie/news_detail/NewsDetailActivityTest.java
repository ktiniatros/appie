package nl.giorgos.appie.news_detail;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import javax.inject.Inject;

import nl.giorgos.appie.MockApp;
import nl.giorgos.appie.MockRule;
import nl.giorgos.appie.R;
import nl.giorgos.appie.inject.TestAppComponent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(AndroidJUnit4.class)
public class NewsDetailActivityTest {
    @Inject
    Picasso picasso;

    @Mock
    RequestCreator requestCreator;

    private Article validArticle = new Article("author", "title", "url", "urlToImage", "content");

    @Rule
    public ActivityTestRule<NewsDetailActivity> activityTestRule = new MockRule<>(NewsDetailActivity.class);

    @Before
    public void setUp() {
        getTestAppComponent().inject(this);
        initMocks(this);
        doNothing().when(requestCreator).into(any(ImageView.class));
        when(picasso.load(anyString())).thenReturn(requestCreator);
        Intents.release();
        Intents.init();
    }

    @Test
    public void launchNewsDetailActivity_withValidArticle_displaysRightContents() {
        // arrange
        Intent startingIntent = new Intent();
        startingIntent.putExtra(NewsDetailActivity.ARTICLE_EXTRA, validArticle);

        // act
        activityTestRule.launchActivity(startingIntent);

        // assert
        onView(withText("title")).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.image_view))
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyBelow(withId(R.id.title_text_view)));
        onView(withText("content"))
            .check(matches(isCompletelyDisplayed()))
            .check(isCompletelyBelow(withId(R.id.image_view)));
    }

    private TestAppComponent getTestAppComponent() {
        MockApp mockApp = (MockApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
        return (TestAppComponent) mockApp.getComponent();
    }
}
