package com.artemissoftware.tester.coffeecompanion.beveragelist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.artemissoftware.tester.R;
import com.artemissoftware.tester.coffeecompanion.beveragedetail.BeverageDetailActivity;
import com.artemissoftware.tester.coffeecompanion.common.Beverage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

public class BeverageListActivityInstrumentedTest {

    @Rule
    public IntentsTestRule<BeverageListActivity> intentsTestRule = new IntentsTestRule<>(BeverageListActivity.class);




    @Test
    public void testBeverageClick_shouldOpenBeverageListActivity() {

        //onView(withText("Café mocha"))
                //.check(matches(isDisplayed()));
                //Esta verificação só funciona se o item está fora do display
                //Como estou a usar um tablet e o item aparece o teste tem que ser modificado
                //.check(matches(not(isDisplayed())));

        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.scrollToPosition(7));

        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        intended(hasComponent(BeverageDetailActivity.class.getName()));

        //pressBack();

        //onView(withText("Café mocha"))
                //.check(matches(isDisplayed()));
                //Esta verificação só funciona se o item está fora do display
                //Como estou a usar um tablet e o item aparece o teste tem que ser modificado
                //.check(matches(not(isDisplayed())));


    }




    @Test
    public void testMapFloatingActionButton_shouldBeDisplayed() {
        onView(withId(R.id.map_fab))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testToolbarImageView_shouldHaveContentDescription() {
        onView(withId(R.id.toolbar_image))
                .check(matches(withContentDescription(R.string.content_fox_mug)));
    }


    /**
     * The below test is trying to find a single AppCompatTextView, but since there are
     * multiple AppCompatTextViews in our view hierarchy, our test fails with an
     * AmbiguousViewMatcherException. We'll have to have to use a different ViewMatcher :-)
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText_fails() {
        onView(isAssignableFrom(AppCompatTextView.class))
                .check(matches(withText(R.string.app_name_coffe_companion)));
    }


    /**
     * There we go! The AppCompatTextView we're looking for is a child of our Toolbar.
     * By combining matchers to specify this, Espresso is able to find our view and
     * perform our test.
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText() {
        onView(allOf(withParent(isAssignableFrom(Toolbar.class)), isAssignableFrom(AppCompatTextView.class)))
                .check(matches(withText(R.string.app_name_coffe_companion)));
    }


    @Test
    public void testBeveragesRecyclerViewItem_shouldHaveBeverageData() {
        Beverage beverage = new Beverage(
                "Café au lait",
                "A drink made with French-pressed " +
                        "coffee and an added touch of hot milk." +
                        " This drink originates from - you guessed" +
                        " it! - France.",
                "R.drawable.ic_cafe_au_lait"
        );

        onView(withId(R.id.beverages_recycler))
                .check(matches(hasBeverageDataForPosition(0, beverage)));
    }


    private static Matcher<View> hasBeverageDataForPosition(final int position, @NonNull final Beverage beverage){

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("UH OH! Item has beverage data at position: " + position + " : ");
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {

                if (null == recyclerView) {
                    return false;
                }

                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

                if (null == viewHolder) {
                    return false;
                }

                return withChild(withText(beverage.getName())).matches(viewHolder.itemView);
            }
        };
    }
}
