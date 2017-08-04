package nanodegree.hossamhazem.backingapp.utils;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient implements Parcelable{
    float quantity;
    String measure;
    String name;

    public Ingredient(float quantity, String measure, String name) {
        this.quantity = quantity;
        switch(measure){
            case "CUP":
                if(quantity > 1)
                    this.measure = "cups";
                else
                    this.measure = "cup";
                break;
            case "TBLSP":
                if(quantity > 1)
                    this.measure = "tablespoons";
                else
                    this.measure = "tablespoon";
                break;
            case "K":
                if(quantity > 1)
                    this.measure = "kilos";
                else
                    this.measure = "kilo";
                break;
            case "OZ":
                if(quantity > 1)
                    this.measure = "ounces";
                else
                    this.measure = "ounce";
                break;
            case "G":
                if(quantity > 1)
                    this.measure = "grams";
                else
                    this.measure = "gram";
                break;
            case "UNIT":
                if(quantity > 1)
                    this.measure = "units";
                else
                    this.measure = "unit";
                break;
            case "TSP":
                if(quantity > 1)
                    this.measure = "teaspoons";
                else
                    this.measure = "teaspoon";
                break;
            default:
                this.measure = measure;
        }
        this.name = name;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        String quantityString;
        if(quantity == (long) quantity)
            quantityString = String.format("%d",(long)quantity);
        else
            quantityString =  String.format("%s",quantity);
        return quantityString+" "+measure+" of "+name;
    }

    public static Ingredient parse(JSONObject jsonObject) throws JSONException {
        float quantity = (float) jsonObject.getDouble("quantity");
        String measure = jsonObject.getString("measure");
        String ingredient = jsonObject.getString("ingredient");

        return new Ingredient(quantity, measure, ingredient);

    }


}
