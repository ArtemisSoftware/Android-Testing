package com.artemissoftware.tester.coffeecompanion.coffeeshopdetail;

import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.artemissoftware.tester.R;
import com.artemissoftware.tester.coffeecompanion.coffeeshoplist.CoffeeShop;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
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

public class CoffeeShopDetailActivityTest {

    public static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(0);

    @Rule
    public IntentsTestRule<CoffeeShopDetailActivity> intentsTestRule = new IntentsTestRule<CoffeeShopDetailActivity>(CoffeeShopDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            //Intent intent = super.getActivityIntent();
            Intent intent = new Intent();


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