package esi.kaly.com.stormy.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import esi.kaly.com.stormy.R;
import esi.kaly.com.stormy.model.CurrentWeather;
import esi.kaly.com.stormy.service.WeatherService;
import esi.kaly.com.stormy.service.WeatherUpdater;
import esi.kaly.com.stormy.utilities.StormyConstants;

public class MainActivity extends Activity implements WeatherUpdater {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Nullable
    @BindView(R.id.timeLabel)
    TextView mTimeLabel;

    @Nullable
    @BindView(R.id.locationLabel)
    TextView mLocationiLabel;

    @Nullable
    @BindView(R.id.temperatureLabel)
    TextView mTemperatureLabel;

    @Nullable
    @BindView(R.id.humidityValue)
    TextView mHumidityValue;

    @Nullable
    @BindView(R.id.precipValue)
    TextView mPrecipValue;

    @Nullable
    @BindView(R.id.summaryLabel)
    TextView mSummaryLabel;

    @Nullable
    @BindView(R.id.iconImageView)
    ImageView mIconImageView;

    private CurrentWeather mCurrentWeather;
    private WeatherService mWeatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (mWeatherService == null){
            mWeatherService = new WeatherService(this);
        }

        Log.d(TAG, "Main UI code is running!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        retrieveData();
    }

    private void retrieveData() {
        if(isNetworkAvailable()) {
            if (mWeatherService != null){
                mWeatherService.fetchCurrentWeather(StormyConstants.FORECAST_URL);
            }
        } else {
            alertUserAboutError(getString(R.string.network_unavailable_title), getString(R.string.network_unavailable_message));

        }
    }

    private void updatedDisplay() {

        if (mCurrentWeather != null) {
            if (mTemperatureLabel != null) {
                mTemperatureLabel.setText(String.format("%s", mCurrentWeather.getTemperature()));
            }
            if (mLocationiLabel != null) {
                mLocationiLabel.setText(mCurrentWeather.getTimeZone());
            }

            if (mTimeLabel != null) {
                mTimeLabel.setText("At " + mCurrentWeather.getFormattedTime() + " it will be");
            }

            if (mHumidityValue != null) {
                mHumidityValue.setText(String.format("%s", mCurrentWeather.getHumidity()));
            }

            if (mPrecipValue != null) {
                mPrecipValue.setText(String.format("%s %s", mCurrentWeather.getPrecipChance(), "%"));
            }

            if (mSummaryLabel != null) {
                mSummaryLabel.setText(mCurrentWeather.getSummary());
            }

            Drawable drawable = ContextCompat.getDrawable(this, mCurrentWeather.getIconId());
            if (mIconImageView != null) {
                mIconImageView.setImageDrawable(drawable);
            }
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())   {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError(String title, String message) {
        AlertDialogFragment dialog = AlertDialogFragment.newInstance(title, message);
        dialog.show(getFragmentManager(),"error_dialog");
    }

    @Override
    public void currentWeatherRetrieved(final CurrentWeather currentWeather) {
        this.mCurrentWeather = currentWeather;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updatedDisplay();
            }
        });

    }

    @Override
    public void onErrorEncountered() {
        if (isNetworkAvailable()){
            alertUserAboutError(getString(R.string.error_title), getString(R.string.error_message));
        } else {
            alertUserAboutError(getString(R.string.network_unavailable_title), getString(R.string.network_unavailable_message));

        }
    }

}
