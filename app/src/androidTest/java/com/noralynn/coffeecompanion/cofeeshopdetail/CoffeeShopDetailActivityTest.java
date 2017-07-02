package com.noralynn.coffeecompanion.cofeeshopdetail;

import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.noralynn.coffeecompanion.R;
import com.noralynn.coffeecompanion.coffeeshoplist.CoffeeShop;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class CoffeeShopDetailActivityTest {

    public static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(0);

    @Rule
    public IntentsTestRule<CoffeeShopDetailActivity> intentsTestRule = new IntentsTestRule<CoffeeShopDetailActivity>(CoffeeShopDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();

            CoffeeShopDetailModel fakeModel = new CoffeeShopDetailModel();
            fakeModel.setCoffeeShop(fakeCoffeeShop);

            intent.putExtra(CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY, fakeCoffeeShop);
            return intent;
        }
    };

    @Test
    public void testMapButton_SendsMapViewLocationIntent() {
        Uri desiredUri = Uri.parse("geo:0,0?q=" + fakeCoffeeShop.getAddress());

        onView(withId(R.id.button_map)).perform(click());

        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(desiredUri))
        );
    }

}