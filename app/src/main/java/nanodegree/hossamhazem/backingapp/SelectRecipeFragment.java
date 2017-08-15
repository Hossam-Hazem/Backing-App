package nanodegree.hossamhazem.backingapp;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.adapters.RecipeListAdapter;
import nanodegree.hossamhazem.backingapp.utils.Recipe;


public class SelectRecipeFragment extends Fragment{
    @BindView(R.id.recipesRecyclerView) RecyclerView mRecyclerView;
    @BindBool(R.bool.isTablet) boolean isTablet;
    private RecipeListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int lastScrolledPosition;
    ArrayList<Recipe> recipes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecipeListAdapter(getContext(), recipes);


        setRetainInstance(true);

    }

    public static SelectRecipeFragment newInstance(ArrayList<Recipe> recipes){
        SelectRecipeFragment x =  new SelectRecipeFragment();
        x.setRecipes(recipes);
        return x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_recipe, container, false);



        ButterKnife.bind(this, view);

        mRecyclerView.setAdapter(mAdapter);
        if (isTablet) {
            mLayoutManager = new GridLayoutManager(getContext(), 3);
        } else
            mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (isTablet) {
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                    int space = 45;

                    outRect.left = space;
                    outRect.right = space;
                    outRect.bottom = space;
                    outRect.top = space;

                }
            });
        }
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey("lastPosition")){
                int lastPosition = savedInstanceState.getInt("lastPosition");
                mLayoutManager.scrollToPosition(lastPosition);
            }
        }







        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isTablet == false){
            int lastPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            outState.putInt("lastPosition", lastPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null) {

        }
    }

    public void setRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    public void updateRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        mAdapter.replace(recipes);
    }

}


