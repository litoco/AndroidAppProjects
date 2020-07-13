package www.example.com.androidpaginglibrary.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;
import www.example.com.androidpaginglibrary.data.retrofit.RestaurantsData;
import www.example.com.androidpaginglibrary.data.retrofit.RetrofitClientBuilder;

public class ZomatoPagingDataSource extends PositionalDataSource<Restaurant> {

    private int cityCode;
    private String accessToken;
    private RetrofitClientBuilder retrofitClientBuilder = new RetrofitClientBuilder();

    public ZomatoPagingDataSource(int cityCode, String accessToken) {
        this.cityCode = cityCode;
        this.accessToken = accessToken;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<Restaurant> callback) {
        retrofitClientBuilder = new RetrofitClientBuilder();
        retrofitClientBuilder.getZomatoApis().getRestaurantInCity(accessToken, cityCode, "city")
                .enqueue(new Callback<RestaurantsData>() {
                    @Override
                    public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null) {
                                callback.onResult(response.body().getAllRestaurantList(),params.requestedStartPosition, Integer.parseInt(response.body().getTotalNumberOfRestaurants()));
                            }else
                                Log.e("Response","body is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantsData> call, Throwable t) {
                        Log.e("Request","Failed");
                    }
                });
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<Restaurant> callback) {
        retrofitClientBuilder.getZomatoApis().getRestaurantInCity(accessToken, cityCode, "city")
                .enqueue(new Callback<RestaurantsData>() {
                    @Override
                    public void onResponse(Call<RestaurantsData> call, Response<RestaurantsData> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null) {
                                callback.onResult(response.body().getAllRestaurantList());
                            }else
                                Log.e("After Response","body is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantsData> call, Throwable t) {
                        Log.e("After Request","Failed");
                    }
                });
    }
}
