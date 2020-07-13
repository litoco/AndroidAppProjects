package www.example.com.androidpaginglibrary.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class LocationSuggestions {

    @SerializedName("name")
    @Expose
    private String cityName;

    @SerializedName("country_name")
    @Expose
    private String countryName;

    @SerializedName("country_flag_url")
    @Expose
    private String flagUrl;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
