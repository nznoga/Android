package com.nz.submission4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.nz.submission4.Notification.MovieDailyReceiver;
import com.nz.submission4.Notification.MovieReleaseReceiver;


public class SetReminder extends AppCompatActivity {
    private Switch mSwitchReminder;
    private Switch mSwitchRelease;
    private MovieDailyReceiver mMovieDailyReceiver;
    private MovieReleaseReceiver mMovieReleaseReceiver;
    private SettingPreference mSettingPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        mSwitchReminder = findViewById(R.id.switch_daily_reminder);
        mSwitchRelease = findViewById(R.id.switch_release_today);
        mMovieDailyReceiver = new MovieDailyReceiver();
        mMovieReleaseReceiver = new MovieReleaseReceiver();

        mSettingPreference = new SettingPreference(this);
        setSwitchRelease();
        setSwitchReminder();
        mSwitchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchReminder.isChecked()) {
                    mMovieDailyReceiver.setDailyAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mMovieDailyReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSwitchRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchRelease.isChecked()) {
                    mMovieReleaseReceiver.setReleaseAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mMovieReleaseReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setSwitchReminder() {
        if (mSettingPreference.getDailyReminder()) mSwitchReminder.setChecked(true);
        else mSwitchReminder.setChecked(false);
    }

    private void setSwitchRelease() {
        if (mSettingPreference.getReleaseReminder()) mSwitchRelease.setChecked(true);
        else mSwitchRelease.setChecked(false);
    }
}
