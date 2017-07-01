package com.noralynn.coffeecompanion.beveragedetail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.common.Beverage;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.LayoutAssertions.noEllipsizedText;
import static android.support.test.espresso.assertion.LayoutAssertions.noOverlaps;
import static android.support.test.espresso.assertion.PositionAssertions.isAbove;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.noralynn.coffeecompanion.beveragedetail.BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY;

public class BeverageDetailActivityTest {

    @NonNull
    private static final Beverage beverage = Beverage.fake();

    @Rule
    public ActivityTestRule<BeverageDetailActivity> activityTestRule =
        new ActivityTestRule<BeverageDetailActivity>(BeverageDetailActivity.class) {
            @Override
            protected Intent getActivityIntent() {
                Context targetContext = InstrumentationRegistry
                        .getInstrumentation()
                        .getTargetContext();
                Intent result = new Intent(targetContext, BeverageDetailActivity.class);
                result.putExtra(BEVERAGE_MODEL_BUNDLE_KEY, beverage);
                return result;
            }
        };


    @Test
    public void testTitle_shouldBePositionedAboveBeverageImageView() {
        onView(withId(R.id.text_title)).check(isAbove(withId(R.id.image_beverage)));
    }


    @Test
    public void testDescription_shouldBeSetToCorrectBeverageDescriptionText() {
        onView(withId(R.id.text_description)).check(matches(withText(beverage.getDescription())));
    }


    @Test
    public void testImage_shouldBeDisplayed() {
        onView(withId(R.id.image_beverage)).check(matches(isDisplayed()));
    }


    @Test
    public void testDescription_shouldNotContainEllipsizedText() {
        onView(withId(R.id.text_description)).check(noEllipsizedText());
    }


    @Test
    public void testBeverageDetailActivity_shouldNotHaveAnyOverlaps() {
        onView(withId(R.id.activity_beverage)).check(noOverlaps());
    }


}