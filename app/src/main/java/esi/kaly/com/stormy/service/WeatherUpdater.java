package esi.kaly.com.stormy.service;

import esi.kaly.com.stormy.model.CurrentWeather;

/**
 * Created by Ivan on 11/1/2016.
 */

public interface WeatherUpdater {

    void currentWeatherRetrieved(final CurrentWeather currentWeather);

    void onErrorEncountered();
}
