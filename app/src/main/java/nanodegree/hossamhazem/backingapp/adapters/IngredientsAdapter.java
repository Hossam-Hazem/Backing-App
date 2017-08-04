package nanodegree.hossamhazem.backingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nanodegree.hossamhazem.backingapp.R;
import nanodegree.hossamhazem.backingapp.utils.Ingredient;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    ArrayList<Ingredient> ingredients;

    public IngredientsAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView ingredientDesc;
        View mainView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            mainView = itemView;

            ingredientDesc = (TextView) itemView.findViewById(R.id.ingredientItemTextView);
        }

        public void bind(Ingredient ingredient){
            ingredientDesc.setText(ingredient.toString());
        }
    }
}
