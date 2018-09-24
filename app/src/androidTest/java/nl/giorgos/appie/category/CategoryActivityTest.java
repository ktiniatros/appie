package nl.giorgos.appie.category;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nl.giorgos.appie.R;
import nl.giorgos.appie.country_news_list.CountryNewsListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {

    @Rule
    public ActivityTestRule<CategoryActivity> activityTestRule = new ActivityTestRule<>(CategoryActivity.class);

    @Test
    public void launchCategoryActivity_clickOnSwedishFlag_opensSwedishNewsList() {
        // arrange
        Intents.init();
        final String componentName = CountryNewsListActivity.class.getName();
        Intents.intending(hasComponent(componentName))
            .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        // act
        onView(withId(R.id.category_recycler_view))
            .perform(actionOnItem(
                hasDescendant(withText("\uD83C\uDDF8\uD83C\uDDEA")), click()));

        // assert
        Intents.intended(hasComponent(componentName));
        Intents.intended(hasExtra(CountryNewsListActivity.COUNTRY_CODE_EXTRA, "se"));
    }
}
