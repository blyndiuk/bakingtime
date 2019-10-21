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
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepTest {

    public static final String STEP = "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickRecyclerViewItem_StepDescriptionIsSet() {


        //allow some time for data to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.rv_main_activity)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView(ViewMatchers.withId(R.id.rv_master_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, click()));


        onView(ViewMatchers.withId(R.id.tv_step_description)).check(matches(withText(STEP)));
    }
}