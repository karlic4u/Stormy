package esi.kaly.com.stormy.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import esi.kaly.com.stormy.model.CurrentWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ivan on 11/1/2016.
 */

public class WeatherService {

    private static final String TAG = WeatherService.class.getSimpleName();
    private WeatherUpdater mWeatherUpdater;

    public WeatherService(WeatherUpdater weatherUpdater) {
        this.mWeatherUpdater = weatherUpdater;
    }

    public void fetchCurrentWeather(String forecastUrl){
        OkHttpClient client = new OkHttpClient(); //TODO - Use Singleton Pattern.
        Request request = new Request.Builder()
                .url(forecastUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mWeatherUpdater != null){
                    mWeatherUpdater.onErrorEncountered();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "onResponse()");
                    if (response.isSuccessful()) {
                        if (mWeatherUpdater != null){
                            mWeatherUpdater.currentWeatherRetrieved(getCurrentDetails(jsonData));

                        }
                    } else {

                        if (mWeatherUpdater != null){
                            mWeatherUpdater.onErrorEncountered();
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });

    }

    private CurrentWeather getCurrentDetails(String jsonData) {
        try {
            JSONObject forecast = new JSONObject(jsonData);
            String timezone = forecast.optString("timezone");
            Log.i(TAG, "From JSON: " + timezone);

            JSONObject currently = forecast.getJSONObject("currently");

            CurrentWeather currentWeather = new CurrentWeather();
            currentWeather.setHumidity(currently.optDouble("humidity"));
            currentWeather.setTime(currently.optLong("time"));
            currentWeather.setIcon(currently.optString("icon"));
            currentWeather.setPrecipChance(currently.optDouble("precipProbability"));
            currentWeather.setSummary(currently.optString("summary"));
            currentWeather.setTemperature(currently.optDouble("temperature"));
            currentWeather.setTimeZone(timezone);
            Log.d(TAG, currentWeather.toString());

            return currentWeather;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
