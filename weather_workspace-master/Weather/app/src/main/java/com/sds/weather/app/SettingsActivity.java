package com.sds.weather.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sds.weather.app.dto.Location;
import com.sds.weather.app.sqlite.LocationDAO;

/**
 * Created by eee on 2016-11-27.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .add(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        // SQLite
        private LocationDAO locationDAO;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            locationDAO = new LocationDAO(getActivity());

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

            // 앱을 설치할 때 가장 처음에 기본 설정을 사용하도록 설정
            PreferenceManager.setDefaultValues(getActivity(), R.xml.preference, false);

            String locationValue = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            Location locationDTO = locationDAO.selectOne(Integer.parseInt(locationValue));

            // Location Preference 의 summary 를 설정
            String summary;
            if (!locationDTO.getDong().equals("")) {
                summary = locationDTO.getDong();
            } else if (!locationDTO.getGugun().equals("")) {
                summary = locationDTO.getGugun();
            } else {
                summary = locationDTO.getSido();
            }

            Preference location = findPreference(getString(R.string.pref_location_key));
            location.setSummary(summary);
        }
    }
}
