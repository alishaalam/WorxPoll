package com.alisha.pollparticipant;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alisha.R;
import com.alisha.model.WorxPollEvent;

public class PollResponseListAdapter extends ArrayAdapter<WorxPollEvent>{
	
	private final Context mContext;
	private final List<WorxPollEvent> rowItem;
	
	public PollResponseListAdapter(Context mContext,
			List<WorxPollEvent> rowItems) {
		super(mContext,R.layout.poll_response_list_item,rowItems);
		this.mContext = mContext;
		this.rowItem = rowItems;
	}

	@Override
	public int getCount() {
		return rowItem.size();
	}

	@Override
	public WorxPollEvent getItem(int position) {
		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItem.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//Check if convertView; Get a LayoutInflater; Assign the required view to the inflater
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.poll_response_list_item, null);
		}
		
		//Assign the references
		TextView title_value = (TextView) convertView.findViewById(R.id.title_value);
		TextView organizer_value = (TextView) convertView.findViewById(R.id.organizer_value);
		
		//Make an object of a class you want to populate the list with
		WorxPollEvent list_item = rowItem.get(position);
		title_value.setText(list_item.getEventTitle());
		organizer_value.setText(list_item.getEventOrgEmailId());
		
		return convertView;
	}

}
