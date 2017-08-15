package nanodegree.hossamhazem.backingapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindBool;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.adapters.RecipeStepListAdapter;
import nanodegree.hossamhazem.backingapp.utils.Recipe;
import nanodegree.hossamhazem.backingapp.utils.RecipeStep;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepListAdapter.RecipeStepAdapterActivityInterface, ViewRecipeStepFragment.RecipeStepFragmentActivityInterface {
    Recipe recipe;
    @BindBool(R.bool.isTablet) boolean isTablet;
    @IdRes int recipeStepViewContainerId;
    @IdRes int recipeStepSelectContainerId;
    SelectRecipeStepFragment mainRecipeStepFragment;

    final static String SHARED_PREF_NAME = "com.backingapp.widgetData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        getSupportActionBar().setTitle(recipe.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mainRecipeStepFragment = new SelectRecipeStepFragment();
        mainRecipeStepFragment.setRecipe(recipe);

        recipeStepSelectContainerId = isTablet ? R.id.selectRecipeContainer : R.id.mainContainer;
        recipeStepViewContainerId = isTablet ? R.id.viewRecipeContainer : R.id.mainContainer;

        updateWidget();



        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(recipeStepSelectContainerId, mainRecipeStepFragment, "RecipeStepFragment")
                    .commit();
            if (isTablet) {
                RecipeStep recipeStep = recipe.getRecipeSteps().get(0);
                openViewRecipeStepFragment(recipeStepViewContainerId, recipeStep, false);
            }
        }


    }

    private void updateWidget() {
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        editor.putStringSet("data", recipe.getIngredientsInStringSet());
        editor.putString("recipeName",recipe.getName());
        editor.apply();
        Intent i = new Intent(this, HomeBackingAppWidget.class);
        i.setAction(HomeBackingAppWidget.UPDATE_ACTION);
        sendBroadcast(i);
    }

        @Override
    public void recipeStepOnClick(RecipeStep recipeStep) {
        if(isTablet)
            openViewRecipeStepFragment(recipeStepViewContainerId, recipeStep, false);
        else
            openViewRecipeStepFragment(recipeStepViewContainerId, recipeStep, true);
    }

    @Override
    public void nextRecipeStepOnClick(RecipeStep recipeStep) {
        if(recipeStep.getId() != recipe.getRecipeSteps().size() -1){
            RecipeStep nextRecipeStep = recipe.getRecipeSteps().get(recipeStep.getId() + 1);
            openViewRecipeStepFragment(recipeStepViewContainerId, nextRecipeStep, isTablet);

        }
    }

    @Override
    public void prevRecipeStepOnClick(RecipeStep recipeStep) {
        if(recipeStep.getId() != 0) {
            RecipeStep prevRecipeStep = recipe.getRecipeSteps().get(recipeStep.getId() - 1);
            openViewRecipeStepFragment(recipeStepViewContainerId, prevRecipeStep, isTablet);
        }
    }

    public void openViewRecipeStepFragment(@IdRes int containerId, RecipeStep recipeStep, boolean addToBackstack){
        ViewRecipeStepFragment viewRecipeStepFragment = new ViewRecipeStepFragment();

        viewRecipeStepFragment.setRecipeStep(recipeStep);


        if(addToBackstack) {
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId, viewRecipeStepFragment, "viewRecipeFragment")
                    .addToBackStack("viewRecipeFragment")
                    .commitAllowingStateLoss();
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId, viewRecipeStepFragment,"viewRecipeFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed(){
        if(!isTablet) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(recipeStepViewContainerId);
            Object tag = f.getTag();
            if (tag != null && tag.equals("viewRecipeFragment")) {
                fm.beginTransaction()
                        .remove(f)
                        .commit();
            }
        }
        super.onBackPressed();
    }
}
