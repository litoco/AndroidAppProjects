package www.example.com.androidpaginglibrary.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRating {

    @SerializedName("aggregate_rating")
    @Expose
    private String rating;

    @SerializedName("rating_text")
    @Expose
    private String ratingText;

    @SerializedName("rating_color")
    @Expose
    private String ratingColorRGBA;

    @SerializedName("votes")
    @Expose
    private String votes;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getRatingColorRGBA() {
        return ratingColorRGBA;
    }

    public void setRatingColorRGBA(String ratingColorRGBA) {
        this.ratingColorRGBA = ratingColorRGBA;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
