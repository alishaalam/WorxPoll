package com.alisha.pollparticipant;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alisha.R;
import com.alisha.model.WorxPollEventDetails;

public class PollResponseDetailListAdapter extends ArrayAdapter<WorxPollEventDetails> {

	private final Context mContext;
	private final List<WorxPollEventDetails> rowItem;

	/**
	 * @param mContext
	 * @param rowItem
	 */
	public PollResponseDetailListAdapter(Context mContext,
			List<WorxPollEventDetails> rowItem) {
		super(mContext, R.layout.poll_response_detail_list_item, rowItem);
		this.mContext = mContext;
		this.rowItem = rowItem;
	}

	@Override
	public int getCount() {
		return rowItem.size();
	}

	@Override
	public WorxPollEventDetails getItem(int position) {
		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItem.indexOf(getItem(position));
	}

	private class EventDetailViewHolder {
		TextView event_date;
		TextView event_time_from;
		TextView event_time_to;
		CheckBox isDateChecked;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EventDetailViewHolder holder = null;

		// Check if convertview is null
		// Get inflater; assign the view to the view
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(
					R.layout.poll_response_detail_list_item, null);

			holder = new EventDetailViewHolder();
			holder.event_date = (TextView) convertView
					.findViewById(R.id.event_date);
			holder.event_time_from = (TextView) convertView
					.findViewById(R.id.event_time_from);
			holder.event_time_to = (TextView) convertView
					.findViewById(R.id.event_time_to);
			holder.isDateChecked = (CheckBox) convertView
					.findViewById(R.id.checkbox_time);
			convertView.setTag(holder);

			holder.isDateChecked.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					WorxPollEventDetails poll_detail = (WorxPollEventDetails) cb.getTag();
					poll_detail.setEventSelected(cb.isChecked());
				}
			});
		} else {
			holder = (EventDetailViewHolder) convertView.getTag();
		}

		// Make an object of the class with which you want to populate the view
		// with
		WorxPollEventDetails list_item = rowItem.get(position);

		holder.event_date.setText(list_item.getEventDate());
		holder.event_time_from.setText(list_item.getEventStartTime());
		holder.event_time_to.setText(list_item.getEventEndTime());
		holder.isDateChecked.setChecked(list_item.isEventSelected());
		holder.isDateChecked.setTag(list_item);

		return convertView;
	}

}
