package com.noralynn.coffeecompanion.beveragelist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.beveragedetail.BeverageDetailActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class BeverageListActivityTest {

    @Rule
    public IntentsTestRule<BeverageListActivity> intentsTestRule = new IntentsTestRule<>(BeverageListActivity.class);

    @Test
    public void testBeverageClick_shouldOpenBeverageDetailActivity() throws Throwable {
        // Setup

        // Act
        onView(withId(R.id.beverages_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Verify
        intended(hasComponent(BeverageDetailActivity.class.getName()));
    }

}