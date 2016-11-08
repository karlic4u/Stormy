package esi.kaly.com.stormy.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import esi.kaly.com.stormy.R;

/**
 * Created by Ivan on 10/7/2016.
 */
public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconId() {

        int iconId = R.drawable.clear_day;

        if(mIcon != null) {
            switch (mIcon) {
                case "clear-day":
                    iconId = R.drawable.clear_day;
                    break;
                case "clear-night":
                    iconId = R.drawable.clear_night;
                    break;
                case "rain":
                    iconId = R.drawable.rain;
                    break;
                case "snow":
                    iconId = R.drawable.snow;
                    break;
                case "sleet":
                    iconId = R.drawable.sleet;
                    break;
                case "wind":
                    iconId = R.drawable.wind;
                    break;
                case "fog":
                    iconId = R.drawable.fog;
                    break;
                case "cloudy":
                    iconId = R.drawable.cloudy;
                    break;
                case "partly-cloudy-day":
                    iconId = R.drawable.partly_cloudy;
                    break;
                case "partly-cloudy-night":
                    iconId = R.drawable.cloudy_night;
                    break;
            }
        }
        return iconId;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime(){
        String timeString = "";
        if (mTimeZone != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
            Date dateTime = new Date(getTime() * 1000);
            timeString = formatter.format(dateTime);
        }
        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {

        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {

        mTemperature = temperature;
    }

    public double getHumidity() {

        return mHumidity;
    }

    public void setHumidity(double humidity) {

        this.mHumidity = humidity;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int)Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {

        mPrecipChance = precipChance;
    }

    public String getSummary() {

        return mSummary;
    }

    public void setSummary(String summary) {

        mSummary = summary;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "mIcon='" + mIcon + '\'' + "\n"+
                ", mTime=" + mTime + "\n"+
                ", mTemperature=" + mTemperature + "\n"+
                ", mHumidity=" + mHumidity + "\n"+
                ", mPrecipChance=" + mPrecipChance + "\n"+
                ", mSummary='" + mSummary + '\'' + "\n"+
                ", mTimeZone='" + mTimeZone + '\'' + "\n"+
                '}';
    }
}
