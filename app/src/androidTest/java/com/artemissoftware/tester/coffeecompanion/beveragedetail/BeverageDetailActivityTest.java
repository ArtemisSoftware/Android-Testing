package com.artemissoftware.tester.coffeecompanion.beveragedetail;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.espresso.assertion.PositionAssertions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.artemissoftware.tester.R;
import com.artemissoftware.tester.coffeecompanion.common.Beverage;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.LayoutAssertions.noEllipsizedText;
import static androidx.test.espresso.assertion.LayoutAssertions.noOverlaps;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import static com.artemissoftware.tester.coffeecompanion.beveragedetail.BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY;

public class BeverageDetailActivityTest {

    @NonNull
    private static final Beverage beverage = Beverage.fake();

    @Rule
    public ActivityTestRule<BeverageDetailActivity> activityTestRule = new ActivityTestRule<BeverageDetailActivity>(BeverageDetailActivity.class) {

        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, BeverageDetailActivity.class);
            result.putExtra(BEVERAGE_MODEL_BUNDLE_KEY, beverage);
            return result;
        }
    };

    @Test
    public void testTitle_shouldBePositionedAboveBeverageImageView() {
        onView(withId(R.id.text_title)).check(isCompletelyAbove(withId(R.id.image_beverage)));
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