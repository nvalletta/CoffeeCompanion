package com.noralynn.coffeecompanion.coffeeshoplist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.noralynn.coffeecompanion.R;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class CoffeeShopListActivityTest {

    private static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(99);

    @Rule
    public IntentsTestRule<CoffeeShopListActivity> intentsTestRule = new IntentsTestRule<CoffeeShopListActivity>(CoffeeShopListActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();

            CoffeeShopListModel fakeModel = new CoffeeShopListModel(true);
            fakeModel.setCoffeeShops(getFakeCoffeeShops());

            intent.putExtra(CoffeeShopListActivity.COFFEE_SHOPS_BUNDLE_KEY, fakeModel);
            return intent;
        }

        @NonNull
        private List<CoffeeShop> getFakeCoffeeShops() {
            List<CoffeeShop> coffeeShops = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                coffeeShops.add(CoffeeShop.fake(i));
            }
            coffeeShops.add(0, fakeCoffeeShop);
            return coffeeShops;
        }
    };

    @Test
    public void testShareButton_sendsCorrectShareIntent() {
        String message = InstrumentationRegistry
                .getTargetContext()
                .getResources()
                .getString(
                        R.string.coffee_shop_share_message,
                        fakeCoffeeShop.getHumanReadableDistance(),
                        fakeCoffeeShop.getName()
                );

        onView(withId(R.id.action_share)).perform(click());

        intended(allOf(
                hasType("text/plain"),
                hasAction(Intent.ACTION_SEND),
                hasExtra(Intent.EXTRA_TEXT, message))
        );
    }

}