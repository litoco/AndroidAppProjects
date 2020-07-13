package www.example.com.androidpaginglibrary.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;

public class Repository {
    public LiveData<PagedList<Restaurant>> getRestaurantDetailList(int cityCode, String accessToken) {
        PagedList.Config myPagingConfig = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(0)
                .setPageSize(20)
                .setPrefetchDistance(60)
                .setEnablePlaceholders(true)
                .build();
        return new LivePagedListBuilder<>(new ZomatoRestaurantDataSourceFactory(cityCode, accessToken), myPagingConfig).build();
    }
}
