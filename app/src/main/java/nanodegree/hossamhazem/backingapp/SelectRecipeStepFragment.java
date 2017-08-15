package nanodegree.hossamhazem.backingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.adapters.RecipeStepListAdapter;
import nanodegree.hossamhazem.backingapp.utils.Recipe;


public class SelectRecipeStepFragment extends Fragment {
    @BindView(R.id.recipeStepsRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.ingredientsButton) Button mIngredientsButton;
    @BindView(R.id.recipeName) TextView mRecipeName;
    private RecipeStepListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Recipe recipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey("recipe")){
            recipe = savedInstanceState.getParcelable("recipe");
        }
        mAdapter = new RecipeStepListAdapter(recipe.getRecipeSteps(),(RecipeStepListAdapter.RecipeStepAdapterActivityInterface)getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe", recipe);

        int lastPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        outState.putInt("lastPosition",lastPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_recipe_step, container, false);
        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecipeName.setText(recipe.getName());

        mIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientsDialogFragment f = IngredientsDialogFragment.newInstance(recipe.getIngredients(), getActivity());
                f.showDialog();
            }
        });

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey("lastPosition")){
                int lastPosition = savedInstanceState.getInt("lastPosition");
                mLayoutManager.scrollToPosition(lastPosition);
            }
        }

        return view;
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

}
