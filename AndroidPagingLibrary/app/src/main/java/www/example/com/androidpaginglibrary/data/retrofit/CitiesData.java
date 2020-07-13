package www.example.com.androidpaginglibrary.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesData {

    @SerializedName("location_suggestions")
    @Expose
    private List<LocationSuggestions> suggestedCitiesList;

    @SerializedName("has_more")
    @Expose
    private int moreSuggestionAvailable;

    public List<LocationSuggestions> getSuggestedCitiesList() {
        return suggestedCitiesList;
    }

    public void setSuggestedCitiesList(List<LocationSuggestions> suggestedCitiesList) {
        this.suggestedCitiesList = suggestedCitiesList;
    }

    public int getMoreSuggestionAvailable() {
        return moreSuggestionAvailable;
    }

    public void setMoreSuggestionAvailable(int moreSuggestionAvailable) {
        this.moreSuggestionAvailable = moreSuggestionAvailable;
    }
}
