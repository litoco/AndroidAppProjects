package www.example.com.androidpaginglibrary.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import www.example.com.androidpaginglibrary.data.Repository;
import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;

public class RestaurantListActivityViewModel extends ViewModel {

    private LiveData<PagedList<Restaurant>> restaurantList;
    private Repository repository;

    public RestaurantListActivityViewModel() {
        this.repository = new Repository();
    }

    public LiveData<PagedList<Restaurant>> getRestaurantList(int cityCode, String accessToken) {
        if(restaurantList==null){
            restaurantList = repository.getRestaurantDetailList(cityCode, accessToken);
        }
        return restaurantList;
    }
}
