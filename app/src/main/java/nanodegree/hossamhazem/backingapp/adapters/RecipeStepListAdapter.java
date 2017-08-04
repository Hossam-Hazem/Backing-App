package nanodegree.hossamhazem.backingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.R;
import nanodegree.hossamhazem.backingapp.utils.RecipeStep;


public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.RecipeStepCardViewHolder> {

    ArrayList<RecipeStep> recipeSteps;
    RecipeStepAdapterActivityInterface activity;

    public RecipeStepListAdapter(ArrayList<RecipeStep> recipeSteps, RecipeStepAdapterActivityInterface activity) {
        this.recipeSteps = recipeSteps;
        this.activity = activity;
    }

    @Override
    public RecipeStepCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.select_recipe_step_item, parent, false);
        return new RecipeStepCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepCardViewHolder holder, final int position) {
        holder.bind(recipeSteps.get(position));
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(v.getContext(), RecipeStepActivity.class);
//                Recipe recipe = recipeSteps.get(position);
//                myIntent.putExtra("recipe", recipe);
//                v.getContext().startActivity(myIntent);
                  activity.recipeStepOnClick(recipeSteps.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }


    class RecipeStepCardViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipeListShortDesc) TextView recipeListShortDesc;
        View mainView;

        public RecipeStepCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mainView = itemView;
        }

        public void bind(RecipeStep step){
            recipeListShortDesc.setText(step.getShortDescription());
        }
    }

    public interface RecipeStepAdapterActivityInterface{
        void recipeStepOnClick(RecipeStep recipeStep);
    }
}
