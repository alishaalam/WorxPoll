package com.alisha.polltiming;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alisha.R;
import com.alisha.model.WorxPollEventDetails;

public class PollTimingAdapter extends ArrayAdapter<WorxPollEventDetails> {

	Context mContext;
	List<WorxPollEventDetails> rowItems;

	public PollTimingAdapter(Context context, List<WorxPollEventDetails> rowItem) {
		super(context, R.layout.poll_timing_list_item, rowItem);
		this.mContext = context;
		this.rowItems = rowItem;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public WorxPollEventDetails getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// check if convertView is null; Get an LayoutInflater; assign the view
		// to the inflater

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.poll_timing_list_item,
					null);

			final PollTimingDetailHolder poll_holder = new PollTimingDetailHolder();
			poll_holder.event_date = (TextView) convertView
					.findViewById(R.id.event_date);
			poll_holder.event_time_from = (EditText) convertView
					.findViewById(R.id.event_time_from);
			poll_holder.event_time_to = (EditText) convertView
					.findViewById(R.id.event_time_to);
			poll_holder.add_time = (ImageButton) convertView
					.findViewById(R.id.add_time);
			
			poll_holder.add_time.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// Add time for an event
					ImageButton ib = (ImageButton) v;
					WorxPollEventDetails event_detail = (WorxPollEventDetails) ib
							.getTag();
					WorxPollEventDetails new_item = new WorxPollEventDetails();
					new_item.setEventDate(event_detail.getEventDate());
					new_item.setEventStartTime("");
					new_item.setEventEndTime("");
					rowItems.add(rowItems.indexOf(event_detail) + 1, new_item);
					notifyDataSetChanged();
				}
			});
			// Listen for time changes on the editText
			poll_holder.event_time_from
					.setOnFocusChangeListener(new OnFocusChangeListener() {
						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							if (!hasFocus) {
								EditText et = (EditText) v;
								WorxPollEventDetails event_detail = (WorxPollEventDetails) et
										.getTag();
								String startTime = et.getText().toString();
								event_detail.setEventStartTime(startTime);
								rowItems.set(rowItems.indexOf(event_detail),
										event_detail);
								notifyDataSetChanged();
							}
						}
					});
			
			poll_holder.event_time_to.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					EditText et = (EditText) v;
					WorxPollEventDetails poll_detail = (WorxPollEventDetails) et
							.getTag();
					String endTime = et.getText().toString();
					poll_detail.setEventEndTime(endTime);
					rowItems.set(rowItems.indexOf(poll_detail),
							poll_detail);
					notifyDataSetChanged();
				}
			});
			
			//Set tags on the view and on the view holder's children
			convertView.setTag(poll_holder);
			poll_holder.add_time.setTag(rowItems.get(position));
			poll_holder.event_time_from.setTag(rowItems.get(position));
			poll_holder.event_time_to.setTag(rowItems.get(position));

		} else {
			((PollTimingDetailHolder) convertView.getTag()).add_time.setTag(rowItems
					.get(position));
			((PollTimingDetailHolder) convertView.getTag()).event_time_from
					.setTag(rowItems.get(position));
			((PollTimingDetailHolder) convertView.getTag()).event_time_to
					.setTag(rowItems.get(position));
		}

		
		// Make an object of the class you want to populate the list with
		WorxPollEventDetails list_item = rowItems.get(position);
		PollTimingDetailHolder holder = (PollTimingDetailHolder) convertView.getTag();
		holder.event_date.setText(list_item.getEventDate());
		holder.event_time_from.setText(list_item.getEventStartTime());
		holder.event_time_to.setText(list_item.getEventEndTime());
		holder.add_time.setImageResource(R.drawable.ic_input_add);
		
		return convertView;
	}

	public static class PollTimingDetailHolder {
		TextView event_date;
		EditText event_time_from;
		EditText event_time_to;
		ImageButton add_time;
	}

}