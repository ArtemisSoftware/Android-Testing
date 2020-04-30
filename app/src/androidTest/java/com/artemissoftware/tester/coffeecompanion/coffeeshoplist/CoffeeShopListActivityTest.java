package com.artemissoftware.tester.coffeecompanion.coffeeshoplist;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.artemissoftware.tester.R;


import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


import static org.junit.Assert.*;

public class CoffeeShopListActivityTest {

    private static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(99);

    @Rule
    public IntentsTestRule<CoffeeShopListActivity> intentsTestRule = new IntentsTestRule<CoffeeShopListActivity>(CoffeeShopListActivity.class) {
        @Override
        protected Intent getActivityIntent() {

            //Intent intent = super.getActivityIntent();
            Intent intent = new Intent();

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