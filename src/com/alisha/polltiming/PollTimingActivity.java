package com.alisha.polltiming;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alisha.R;
import com.alisha.calendarview.CalendarDisplayActivity;

public class PollTimingActivity extends Activity implements
		PollTimingFragment.OnPollDetailSelectedListener {

	private static final String TAG = "PollTimingActivity";
	
	
	public static String RECEIVED_DATE_LIST = "event_date_list";
	ArrayList<String> eventDateList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate called");
		eventDateList = getIntent().getStringArrayListExtra(
				CalendarDisplayActivity.EVENT_DATE_LIST);
		setContentView(R.layout.activity_poll_timing);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onPollSelected(View v, int position, long id) {
		Toast.makeText(this, "Pressed from the main", Toast.LENGTH_SHORT)
				.show();
	}

	public ArrayList<String> getDateList() {
		Log.i("Alishaa-activity", "Datelist size" + eventDateList.size());
		return eventDateList;
	}

}
