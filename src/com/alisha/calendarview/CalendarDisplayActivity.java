package com.alisha.calendarview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.GridView;
import android.widget.Toast;

import com.alisha.R;
import com.alisha.polltiming.PollTimingActivity;

public class CalendarDisplayActivity extends Activity {

	private CalendarView calendarView;
	private Calendar selectedDate;
	public ArrayList<Date> dateList = new ArrayList<Date>();
	ArrayList<String> formattedDateList = new ArrayList<String>();
	GridView grid;

	private static String TAG = "CalendarDisplayActivity";
	public static String EVENT_DATE_LIST = "event_date_list";

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_display);
		grid = (GridView) findViewById(R.id.gridViewDates);

		calendarView = (CalendarView) findViewById(R.id.calendar_view);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState != null){
			dateList = (ArrayList<Date>) savedInstanceState.getSerializable(EVENT_DATE_LIST); 
		}

		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				selectedDate = new GregorianCalendar(year, month + 1,
						dayOfMonth); 
				if (!dateList.contains(selectedDate.getTime()))
					dateList.add(selectedDate.getTime());
				else 
					Toast.makeText(getApplicationContext(), "Selected date already exists", Toast.LENGTH_SHORT).show();
				displaySelectedDates();
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EVENT_DATE_LIST, dateList);
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		if (savedInstanceState != null) {
			dateList = (ArrayList<Date>) savedInstanceState.getSerializable(EVENT_DATE_LIST);
		}
		displaySelectedDates();
	}
	
	/*
	*  Convert raw dates to formatteddateList
	*/
	public ArrayList<String> converDateToFormattedString(ArrayList<Date> dateList){
		ArrayList<String> fd = new ArrayList<String>();
		for(Date d : dateList) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
			String formattedDate = sdf.format(d);
			fd.add(formattedDate);
		}
		return fd;
	}

	public void displaySelectedDates() {
		formattedDateList = converDateToFormattedString(dateList);
		ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, formattedDateList);
		grid.setAdapter(adp);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getBaseContext(), "Removed:" + formattedDateList.remove(arg2),
						Toast.LENGTH_SHORT).show();
				dateList.remove(arg2);
				displaySelectedDates();	
			}
		});
	}

	/**
	 * Print the dates stored in the List
	 */
	public void printDateList() {
		for (Date d : dateList) {
			Log.d(TAG, d.toString());
		}
	}
	
	
	/**
	 * Start the PollTimingActivity on button press
	 */
	public void showTiming(View v) {
		if (dateList.size() > 0) {
		Intent i = new Intent(this, PollTimingActivity.class);
		i.putStringArrayListExtra(EVENT_DATE_LIST, formattedDateList);
		startActivity(i);
		}
		else 
			Toast.makeText(this, "Select atleast one date", Toast.LENGTH_LONG).show();
	}
}