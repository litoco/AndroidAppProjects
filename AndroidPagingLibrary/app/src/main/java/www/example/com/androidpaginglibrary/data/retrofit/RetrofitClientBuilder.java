package www.example.com.androidpaginglibrary.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBuilder {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://developers.zomato.com/api/v2.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ZomatoApi getZomatoApis() {
        return retrofit.create(ZomatoApi.class);
    }
}
