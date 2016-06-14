package com.alisha.pollerdetails;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.alisha.R;
import com.alisha.calendarview.CalendarDisplayActivity;
import com.alisha.model.Validation;
import com.alisha.model.WorxPollConstants;

public class PollerDetailsActivity extends Activity implements
		OnFocusChangeListener {

	private static final String TAG = "PollerDetailsActivity";
	public static final String PREFS_NAME = "WorxPollPrefsFile";
	private EditText mEventTitle;
	private EditText mEventLocationField;
	private EditText mOrgNameField;
	private EditText mOrgEmailField;

	public SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_poller_details);

		mEventTitle = (EditText) findViewById(R.id.editTextTitle);
		mEventLocationField = (EditText) findViewById(R.id.editTextLocation);
		mOrgNameField = (EditText) findViewById(R.id.editTextOrgName);
		mOrgEmailField = (EditText) findViewById(R.id.editTextOrgEmail);

		mEventTitle.setOnFocusChangeListener(this);
		mEventLocationField.setOnFocusChangeListener(this);
		mOrgNameField.setOnFocusChangeListener(this);
		mOrgEmailField.setOnFocusChangeListener(this);
		
		// Restore preferences
		populateFromSharedPreferences();
	}

	private void populateFromSharedPreferences() {
		preferences = getSharedPreferences(PREFS_NAME, 0);
		mOrgNameField.setText(preferences.getString(WorxPollConstants.ORGANIZERNAME, ""));
		mOrgEmailField.setText(preferences.getString(WorxPollConstants.ORGANIZEREMAIL, ""));
	}


	public void onNext(View v) {
		loadSharedPreferences();
		if (isRequiredFieldsComplete()) {
			Intent i = new Intent(PollerDetailsActivity.this,
					CalendarDisplayActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(getApplicationContext(),
					"Please fix the incorrect fields.", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void loadSharedPreferences() {

		String title = mEventTitle.getText().toString();
		String location = mEventLocationField.getText().toString();
		String orgName = mOrgNameField.getText().toString();
		String orgEmail = mOrgEmailField.getText().toString();
		
		preferences = getSharedPreferences(PREFS_NAME, 0);
		
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(WorxPollConstants.ORGANIZERNAME, orgName);
		editor.putString(WorxPollConstants.ORGANIZEREMAIL, orgEmail);
		editor.putString(WorxPollConstants.EVENTTITLE, title);
		editor.putString(WorxPollConstants.EVENTLOCATION, location);
		editor.commit();

	}

	private boolean isRequiredFieldsComplete() {
		boolean ret = true;
		if (!Validation.isValidPersonName(mOrgNameField, true))
			ret = false;
		if (!Validation.isValidEmailAddress(mOrgEmailField, true))
			ret = false;
		if (!Validation.isValidTitle(mEventTitle, true))
			ret = false;
		return ret;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateFromSharedPreferences();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		loadSharedPreferences();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		populateFromSharedPreferences();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		loadSharedPreferences();
	}

	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			EditText changedEditText = (EditText) v;
			if (changedEditText == mOrgNameField) {
				Validation.isValidPersonName(mOrgNameField, true);
			} else if (changedEditText == mOrgEmailField) {
				Validation.isValidEmailAddress(mOrgEmailField, true);
			} else if (changedEditText == mEventLocationField) {
				Validation.isValidLocation(mEventLocationField, false);
			} else if (changedEditText == mEventTitle) {
				Validation.isValidTitle(mEventTitle, true);
			}

		}

	}
}
