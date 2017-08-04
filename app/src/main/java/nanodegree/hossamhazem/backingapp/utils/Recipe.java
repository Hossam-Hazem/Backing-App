package nanodegree.hossamhazem.backingapp.utils;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Recipe implements Parcelable{
    int id;
    String name;
    ArrayList<Ingredient> ingredients;
    ArrayList<RecipeStep> recipeSteps;
    int servings;
    Uri image;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
        this.servings = servings;
        if(!image.isEmpty())
            this.image = Uri.parse(image);
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            recipeSteps = new ArrayList<RecipeStep>();
            in.readList(recipeSteps, RecipeStep.class.getClassLoader());
        } else {
            recipeSteps = null;
        }
        servings = in.readInt();
        image = (Uri) in.readValue(Uri.class.getClassLoader());
    }

    public Set<String> getIngredientsInStringSet(){
        Set<String> result = new HashSet<>();
        for(Ingredient ingredient : ingredients){
            result.add(ingredient.toString());
        }
        return  result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (recipeSteps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(recipeSteps);
        }
        dest.writeInt(servings);
        dest.writeValue(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public int getServings() {
        return servings;
    }

    public Uri getImage() {
        return image;
    }

    public boolean hasImage(){
        return image != null;
    }

    public static Recipe parse(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        int servings = jsonObject.getInt("servings");
        String image = jsonObject.getString("image");

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        JSONArray ingredientsJson = jsonObject.getJSONArray("ingredients");
        for(int c = 0; c < ingredientsJson.length(); c++){
            JSONObject ingredientJson = ingredientsJson.getJSONObject(c);
            ingredients.add(Ingredient.parse(ingredientJson));
        }

        ArrayList<RecipeStep> steps = new ArrayList<>();
        JSONArray stepsJson = jsonObject.getJSONArray("steps");
        for(int c = 0; c < stepsJson.length(); c++){
            JSONObject stepJson = stepsJson.getJSONObject(c);
            steps.add(RecipeStep.parse(stepJson));
        }

        return new Recipe(id, name, ingredients, steps, servings, image);

    }
}
