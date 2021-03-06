package nanodegree.hossamhazem.backingapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickRecipe_OpensRecipeStepActivity(){
        onView(withId(R.id.recipesRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipeName)).check(matches(withText("Nutella Pie")));
    }
}
