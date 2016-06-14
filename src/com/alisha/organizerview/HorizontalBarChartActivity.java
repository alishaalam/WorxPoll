package com.alisha.organizerview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.alisha.R;
import com.alisha.host.HostActivity;
import com.alisha.polltiming.PollDetailItem;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

public class HorizontalBarChartActivity extends FragmentActivity implements
		OnSeekBarChangeListener, OnChartValueSelectedListener {

	static final int START_WORXMAIL_CALENDAR = 1;  // The request code
	protected HorizontalBarChart mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_horizontalbarchart);

		mChart = (HorizontalBarChart) findViewById(R.id.chart1);
		mChart.setOnChartValueSelectedListener(this);
		// mChart.setHighlightEnabled(false);

		mChart.setDrawBarShadow(false);

		mChart.setDrawValueAboveBar(true);

		mChart.setDescription("");

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);

		// draw shadows for each bar that show the maximum value
		// mChart.setDrawBarShadow(true);

		// mChart.setDrawXLabels(false);

		mChart.setDrawGridBackground(false);
		
		
		// mChart.setDrawYLabels(false);

		//tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

		XAxis xl = mChart.getXAxis();
		xl.setPosition(XAxisPosition.BOTTOM);
		//xl.setTypeface(tf);
		xl.setDrawAxisLine(true);
		xl.setDrawGridLines(true);
		xl.setGridLineWidth(0.3f);
		//xl.setGridColor(Color.WHITE);

		YAxis yl = mChart.getAxisLeft();
		//yl.setTypeface(tf);
		yl.setDrawAxisLine(true);
		yl.setDrawGridLines(true);
		yl.setGridLineWidth(0.3f);
		//yl.setGridColor(Color.WHITE);
		// yl.setInverted(true);

		YAxis yr = mChart.getAxisRight();
		//yr.setTypeface(tf);
		yr.setDrawAxisLine(true);
		yr.setDrawGridLines(false);
		// yr.setInverted(true);

		setData(6, 18);
		//setTitleColor(Color.WHITE);
		mChart.animateY(2500);


		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setFormSize(8f);
		l.setXEntrySpace(4f);

		// mChart.setDrawLegend(false);
	}


	private void setData(int count, int range) {
		//TODO: Replace with Parse Data
		String[] mMonths = {"Test", "Test"};
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			xVals.add(mMonths[i % mMonths.length]);
		}

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

		for (int i = 0; i < count; i++) {
			int mult = (range + 1);
			int val = (int) (Math.random() * mult);
			yVals1.add(new BarEntry(val, i));
		}

		BarDataSet set1 = new BarDataSet(yVals1, "Participants");
		set1.setBarSpacePercent(35f);
		//set1.setValueTextColor(Color.WHITE);

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);

		BarData data = new BarData(xVals, dataSets);
		data.setValueTextSize(10f);
		//data.setValueTypeface(tf);
		//data.setValueTextColor(Color.WHITE);

		mChart.setData(data);
	}

	@SuppressLint("NewApi")
	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

		if (e == null)
			return;

		RectF bounds = mChart.getBarBounds((BarEntry) e);
		PointF position = mChart.getPosition(e, mChart.getData()
				.getDataSetByIndex(dataSetIndex).getAxisDependency());

		Log.i("bounds", bounds.toString());
		Log.i("position", position.toString());
		
		int selectedDate = e.getXIndex() + 15;
		confirmUserSelection(new PollDetailItem("1", "06/" + Integer.toString(selectedDate) + "/2014", "1pm", "2pm", 20));
	}

	public void onNothingSelected() {
	};
	
	public void confirmUserSelection(PollDetailItem selected) {
		final PollDetailItem selection = selected;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			// set title
			alertDialogBuilder.setTitle("Finalize "+ selection.getEventDate() + " ??");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Click yes to to create a calendar invite!")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, create calendar invite
						createCalendarinvite(selection);
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}

	public void createCalendarinvite(PollDetailItem selected) {
		final Intent calendarintent = new Intent(Intent.ACTION_INSERT);
		calendarintent.setType("vnd.android.cursor.item/event");
		calendarintent.putExtra(Intent.EXTRA_SUBJECT, "Connect Demo");
		startActivityForResult(calendarintent, START_WORXMAIL_CALENDAR);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//After sending calendar invitation Finish HorizontaBarChartActivity and return to Hostactivity
   	 Intent intent = new Intent(this, HostActivity.class);
   	 intent.putExtra("TAB_POSITION", 0);
   	 startActivity(intent);
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}