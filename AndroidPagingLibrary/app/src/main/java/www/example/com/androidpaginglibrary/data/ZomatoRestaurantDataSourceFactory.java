package www.example.com.androidpaginglibrary.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;

public class ZomatoRestaurantDataSourceFactory extends DataSource.Factory<Integer, Restaurant> {

    private MutableLiveData<ZomatoPagingDataSource> sourceLiveData = new MutableLiveData<>();
    private ZomatoPagingDataSource latestSource;
    private int cityCode;
    private String accessToken;

    public ZomatoRestaurantDataSourceFactory(int cityCode, String accessToken) {
        this.cityCode = cityCode;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public DataSource<Integer, Restaurant> create() {
        latestSource = new ZomatoPagingDataSource(cityCode, accessToken);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
