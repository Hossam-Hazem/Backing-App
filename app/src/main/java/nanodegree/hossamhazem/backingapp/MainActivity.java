package nanodegree.hossamhazem.backingapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import nanodegree.hossamhazem.backingapp.utils.NetworkUtils;
import nanodegree.hossamhazem.backingapp.utils.Recipe;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String>{

    private final String recipesUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int GET_RECIPES_LOADER  = 11;
    ArrayList<Recipe> recipes;
    SelectRecipeFragment selectRecipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null && savedInstanceState.containsKey("recipes")){
            recipes = (ArrayList<Recipe>) savedInstanceState.getSerializable("recipes");
        }
        else{
            recipes = new ArrayList<>();
        }

        LoaderManager loaderManager = this.getSupportLoaderManager();
        Loader<String> recipesLoader = loaderManager.getLoader(GET_RECIPES_LOADER);

        if(recipesLoader == null){
            loaderManager.initLoader(GET_RECIPES_LOADER, null,  this);
        }

        selectRecipeFragment = SelectRecipeFragment.newInstance();
        selectRecipeFragment.setRecipes(recipes);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainContainer, selectRecipeFragment,"selectRecipeFragment")
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("recipes", recipes);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {


                // COMPLETED (8) Force a load
                forceLoad();
            }

            @Override
            public String loadInBackground() {

                try {
                    return NetworkUtils.getResponseFromHttpUrl(new URL(recipesUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            setRecipes(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    private void setRecipes(String jsonString) throws JSONException {
        ArrayList<Recipe> result = new ArrayList<>();
        JSONArray recipesArray = new JSONArray(jsonString);
        for(int c = 0; c<recipesArray.length(); c++){
            JSONObject recipeJson = recipesArray.getJSONObject(c);
            Recipe recipe = Recipe.parse(recipeJson);
            result.add(recipe);
        }
        updateRecipes(result);
    }


    public void updateRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        selectRecipeFragment.updateRecipes(recipes);
    }


}
