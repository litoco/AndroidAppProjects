package www.example.com.androidpaginglibrary.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsData {

    @SerializedName("results_found")
    @Expose
    private String totalNumberOfRestaurants;

    @SerializedName("results_start")
    @Expose
    private String startingIndexOfRestaurants;

    @SerializedName("results_shown")
    @Expose
    private String numberOfRestaurantsPerPage;

    @SerializedName("restaurants")
    @Expose
    private List<Restaurant> allRestaurantList;



    public String getTotalNumberOfRestaurants() {
        return totalNumberOfRestaurants;
    }

    public void setTotalNumberOfRestaurants(String totalNumberOfRestaurants) {
        this.totalNumberOfRestaurants = totalNumberOfRestaurants;
    }

    public String getStartingIndexOfRestaurants() {
        return startingIndexOfRestaurants;
    }

    public void setStartingIndexOfRestaurants(String startingIndexOfRestaurants) {
        this.startingIndexOfRestaurants = startingIndexOfRestaurants;
    }

    public String getNumberOfRestaurantsPerPage() {
        return numberOfRestaurantsPerPage;
    }

    public void setNumberOfRestaurantsPerPage(String numberOfRestaurantsPerPage) {
        this.numberOfRestaurantsPerPage = numberOfRestaurantsPerPage;
    }

    public List<Restaurant> getAllRestaurantList() {
        return allRestaurantList;
    }

    public void setAllRestaurantList(List<Restaurant> allRestaurantList) {
        this.allRestaurantList = allRestaurantList;
    }
}
