package nanodegree.hossamhazem.backingapp;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import nanodegree.hossamhazem.backingapp.utils.Ingredient;
import nanodegree.hossamhazem.backingapp.utils.Recipe;
import nanodegree.hossamhazem.backingapp.utils.RecipeStep;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
public class RecipeStepAcitivityBasicTest {
    @Rule public ActivityTestRule<RecipeStepActivity> mActivityTestRule
            = new ActivityTestRule(RecipeStepActivity.class, true, false);
    Intent initializeIntent;

    public RecipeStepAcitivityBasicTest(){
        initializeIntent = new Intent();

        RecipeStep recipeStep1 = new RecipeStep(0,"testStep0","longTestStep0", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", "");
        RecipeStep recipeStep2 = new RecipeStep(1,"testStep1","longTestStep1", "", "");
        ArrayList<RecipeStep> recipeSteps = new ArrayList<>();
        recipeSteps.add(recipeStep1);
        recipeSteps.add(recipeStep2);

        Ingredient ingredient0 = new Ingredient(2,"CUP","Graham Cracker crumbs");
        Ingredient ingredient1 = new Ingredient(6,"TBLSP","unsalted butter, melted");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient0);
        ingredients.add(ingredient1);

        Recipe recipe = new Recipe(1,"testRecipe",ingredients,recipeSteps,8,"");

        initializeIntent.putExtra("recipe", recipe);

    }


    @Test
    public void clickRecipeStep_OpensViewRecipeStepFragment(){
        mActivityTestRule.launchActivity(initializeIntent);
        onView(withId(R.id.recipeStepsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipeStepDescription)).check(matches(withText("longTestStep0")));
    }

    @Test
    public void clickNextRecipeStepButton_ReOpensViewRecipeStepFragment(){
        mActivityTestRule.launchActivity(initializeIntent);
        assumeTrue(!isScreenSw600dp());
        onView(withId(R.id.recipeStepsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.nextRecipeStepButton))
                .perform(click());
        onView(withId(R.id.recipeStepDescription)).check(matches(withText("longTestStep1")));
    }

    @Test
    public void clickPrevRecipeStepButton_ReOpensViewRecipeStepFragment(){
        mActivityTestRule.launchActivity(initializeIntent);
        assumeTrue(!isScreenSw600dp());
        onView(withId(R.id.recipeStepsRecyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.nextRecipeStepButton))
                .perform(click());
        onView(withId(R.id.prevRecipeStepButton))
                .perform(click());
        onView(withId(R.id.recipeStepDescription)).check(matches(withText("longTestStep0")));
    }

    private boolean isScreenSw600dp(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivityTestRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        return widthDp >= 600;
    }
}
