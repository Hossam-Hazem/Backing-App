package nanodegree.hossamhazem.backingapp.utils;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeStep implements Parcelable{
    int id;
    String shortDescription;
    String description;
    Uri mediaURL;
    MediaType mediaType;

    private RecipeStep(int id, String shortDescription, String description) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
    }

    public RecipeStep(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        this(id, shortDescription, description);
        if(!videoURL.isEmpty()){
            mediaType = MediaType.VIDEO;
            mediaURL = Uri.parse(videoURL);
        }
        else{
            if(!thumbnailURL.isEmpty()){
                mediaType = MediaType.IMAGE;
                mediaURL = Uri.parse(thumbnailURL);
            }
            else{
                mediaType = MediaType.NONE;
            }
        }
    }

    protected RecipeStep(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        mediaURL = (Uri) in.readValue(Uri.class.getClassLoader());
        mediaType = (MediaType) in.readValue(MediaType.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeValue(mediaURL);
        dest.writeValue(mediaType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public Uri getMediaURL() {
        return mediaURL;
    }

    public MediaType getMediaType(){
        return mediaType;
    }

    public static RecipeStep parse(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String shortDesc = jsonObject.getString("shortDescription");
        String desc = jsonObject.getString("description");
        String videoUrl = jsonObject.getString("videoURL");
        String thumbUrl = jsonObject.getString("thumbnailURL");

        return new RecipeStep(id, shortDesc, desc, videoUrl, thumbUrl);
    }
}
