package nl.giorgos.appie;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import nl.giorgos.appie.base.BaseActivity;

public class MockRule<A extends BaseActivity> extends ActivityTestRule<A> {
    public MockRule(Class<A> activityClass) {
        super(activityClass, false, false);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
    }
}
