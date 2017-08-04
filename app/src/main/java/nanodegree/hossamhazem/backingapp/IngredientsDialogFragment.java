package nanodegree.hossamhazem.backingapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.adapters.IngredientsAdapter;
import nanodegree.hossamhazem.backingapp.utils.Ingredient;


public class IngredientsDialogFragment extends DialogFragment {
    @BindView(R.id.ingredientsRecyclerView) RecyclerView mRecyclerView;
    ArrayList<Ingredient> ingredients;
    FragmentActivity activity;
    private IngredientsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new IngredientsAdapter(ingredients);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.dialog_fragment_ingredients, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }



    static IngredientsDialogFragment newInstance(ArrayList<Ingredient> ingredients, FragmentActivity activity) {
        IngredientsDialogFragment f = new IngredientsDialogFragment();
        f.setActivity(activity);
        f.setIngredients(ingredients);

        return f;
    }
    void showDialog() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        this.show(fragmentManager, null);
    }

    void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    void setActivity(FragmentActivity activity){
        this.activity = activity;
    }
}
