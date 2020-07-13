package www.example.com.androidpaginglibrary.data.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ZomatoApi {

    @GET("cities")
    Call<CitiesData> getCitiesMatchingWithEnteredName(@Header("user-key") String userKey, @Query("q") String enteredCityName);

    @GET("search")
    Call<RestaurantsData> getRestaurantInCity(@Header("user-key") String userKey, @Query("entity_id") int cityId, @Query("entity_type") String cityOrLocationCoordinate);
}
