package com.alisha.organizerview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.alisha.R;
import com.alisha.polltiming.PollDetailItem;

public class PollReplyListAdapter extends BaseAdapter{
	
	private final Context mContext;
	private final List<PollDetailItem> rowItem;
	
	@InjectView(R.id.title_value) TextView title_value;
	@InjectView(R.id.respondents_value) TextView respondents_value;
	@InjectView(R.id.invitees_value) TextView invitees_value;

	public PollReplyListAdapter(Context mContext,
			List<PollDetailItem> rowItem) {
		this.mContext = mContext;
		this.rowItem = rowItem;
	}

	@Override
	public int getCount() {
		return rowItem.size();
	}

	@Override
	public Object getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.pollreply_list_item, null);
		}
		
		//Injecting the views
		ButterKnife.inject(this, convertView);
		
		//Make an object of a class you want to populate the list with
		PollDetailItem list_item = rowItem.get(position);
		title_value.setText(list_item.getTitle());
		respondents_value.setText(Integer.toString(list_item.getRespondents()));
		invitees_value.setText(Integer.toString(list_item.getInvitees()));
		
		return convertView;
	}

}
