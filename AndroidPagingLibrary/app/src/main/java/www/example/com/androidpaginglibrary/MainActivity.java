package www.example.com.androidpaginglibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import www.example.com.androidpaginglibrary.data.retrofit.Restaurant;
import www.example.com.androidpaginglibrary.data.retrofit.RestaurantDetails;
import www.example.com.androidpaginglibrary.view.RestaurantAdapter;
import www.example.com.androidpaginglibrary.viewmodel.RestaurantListActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private RestaurantListActivityViewModel restaurantListViewModel;
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(restaurantAdapter);
        restaurantListViewModel.getRestaurantList(40, getString(R.string.access_token)).observe(this, new Observer<PagedList<Restaurant>>() {
            @Override
            public void onChanged(PagedList<Restaurant> restaurant) {
                restaurantAdapter.submitList(restaurant);
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        restaurantAdapter = new RestaurantAdapter(this);
        restaurantListViewModel = new ViewModelProvider(this).get(RestaurantListActivityViewModel.class);
    }

    private void TODOS(){
        //TODO:
        // 1. Setup retrofit first
        // 2. Find the use of data source factory
        // 3. How integrating retrofit results to paging library works
    }
}
