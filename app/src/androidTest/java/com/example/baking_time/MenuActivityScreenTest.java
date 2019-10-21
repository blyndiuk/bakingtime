
package com.example.baking_time;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baking_time.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;


@RunWith(AndroidJUnit4.class)
public class MenuActivityScreenTest {

    public static final String STEP = "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a RecycleView item and checks it opens up the MasterListActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_WidgetButtonIsVisible() {

        //allow some time for data to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.rv_main_activity)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.btn_display_in_widget)).check(matches(isDisplayed()));
    }
}