package nanodegree.hossamhazem.backingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.R;
import nanodegree.hossamhazem.backingapp.RecipeStepActivity;
import nanodegree.hossamhazem.backingapp.utils.Recipe;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    ArrayList<Recipe> recipes;
    Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        this.mContext = context;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.select_recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        holder.bind(recipes.get(position));
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), RecipeStepActivity.class);
                Recipe recipe = recipes.get(position);
                myIntent.putExtra("recipe", recipe);
                v.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void add(Recipe recipe){
        recipes.add(recipe);
        notifyDataSetChanged();
    }

    public void addAll(Collection<Recipe> recipes){
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public void replace(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.selectRecipeName) TextView selectRecipeName;
        @BindView(R.id.selectRecipeServes) TextView selectRecipeServes;
        @BindView(R.id.selectRecipeImage) ImageView selectRecipeImage;
        View mainView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            selectRecipeName.setText(recipe.getName());
            selectRecipeServes.setText("serves to " + recipe.getServings() + " people");
            if (recipe.hasImage()) {
                Picasso.with(mContext).load(recipe.getImage()).centerCrop().into(selectRecipeImage);
            }
            else{

            }
        }
    }
}
