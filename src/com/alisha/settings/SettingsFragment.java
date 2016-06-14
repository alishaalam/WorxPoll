package com.alisha.settings;

import com.alisha.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	public static final String KEY_PREF_IND_RES = "individual_response_preference";
	public static final String KEY_PREF_ALL_RES = "all_response_preference";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(KEY_PREF_IND_RES)) {
			CheckBoxPreference indPref = (CheckBoxPreference) findPreference(key);
			indPref.isChecked();
		}else if (key.equals(KEY_PREF_ALL_RES)) {
			CheckBoxPreference allPref = (CheckBoxPreference) findPreference(key);
			allPref.isChecked();
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
        .registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
        .unregisterOnSharedPreferenceChangeListener(this);
	}
}