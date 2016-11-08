package esi.kaly.com.stormy.utilities;

/**
 * Created by Ivan on 11/1/2016.
 */

public class StormyConstants {

    private static final String BASE_URL = "https://api.darksky.net/forecast/";
    private static final String API_KEY = "32b4de8c5048f5a73410930128227279";
    private static final double LATITUDE = 37.8267;
    private static final double LONGITUDE = -122.4233;
    public static final String FORECAST_URL = BASE_URL + API_KEY + "/" + LATITUDE + "," + LONGITUDE;
}
