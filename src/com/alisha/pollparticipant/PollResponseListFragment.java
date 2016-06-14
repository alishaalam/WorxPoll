package com.alisha.pollparticipant;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alisha.R;
import com.alisha.model.WorxPollEvent;
import com.alisha.model.WorxPollInvitations;
import com.alisha.polltiming.PollTimingFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * A list fragment representing a list of Polls. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link PollResponseDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the
 * {@link OnPollSelectedListener} interface.
 */
public class PollResponseListFragment extends ListFragment {

	PollResponseListAdapter adapter;
	static final String TAG = "PollResponseListFragment";
	List<WorxPollEvent> eventList = new ArrayList<WorxPollEvent>();

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PollResponseListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchInvitesForCurrentUserFromParse();
	}
	
	private void fetchInvitesForCurrentUserFromParse() {
		
		//Create a query on the event: Fetch all events for which the current user is invited
		ParseQuery<WorxPollInvitations> iquery = ParseQuery.getQuery(WorxPollInvitations.class);
		iquery.whereEqualTo("userEmail", ParseUser.getCurrentUser().getEmail());
		
		ParseQuery<WorxPollEvent> query = ParseQuery.getQuery(WorxPollEvent.class);
		query.whereMatchesQuery("invitees", iquery);
		query.findInBackground(new FindCallback<WorxPollEvent>() {
			public void done(List<WorxPollEvent> list, ParseException e) {
				if (e == null) {
	    			eventList = list;
	    			adapter.clear();
	    			adapter.addAll(eventList);
	    			adapter.notifyDataSetChanged();
					for (WorxPollEvent event : list) {
		    			WorxPollEvent.EVENT_ITEM_MAP.put(event.getObjectId(), event);
					}
				} 
				else {
					Log.d(TAG, "Error: " + e.getMessage());
				}	
			}
		});		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_poll_response_list,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new PollResponseListAdapter(getActivity(), eventList);
		setListAdapter(adapter);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	
	  @Override public void onAttach(Activity activity) {
		  super.onAttach(activity);
	  }

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	  
	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		WorxPollEvent event = eventList.get(position);
		onItemSelected(event.getObjectId(), event.getEventTitle(), event.getEventOrgEmailId());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != AdapterView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? AbsListView.CHOICE_MODE_SINGLE
						: AbsListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == AdapterView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	public void onItemSelected(String id, String title, String orgEmailId) {
		Intent detailIntent = new Intent(getActivity(), PollResponseDetailActivity.class);
		detailIntent.putExtra(PollResponseDetailFragment.ARG_ITEM_ID, id);
		detailIntent.putExtra(PollResponseDetailFragment.ARG_TITLE, title);
		detailIntent.putExtra(PollResponseDetailFragment.ARG_ORG, orgEmailId);
		startActivity(detailIntent);
	}
}
