package com.alisha.pollparticipant;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alisha.R;
import com.alisha.host.HostActivity;
import com.alisha.model.WorxPollEvent;
import com.alisha.model.WorxPollEventDetails;
import com.alisha.model.WorxPollUserSelection;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A fragment representing a single Poll detail screen. This fragment is either
 * contained in a {@link PollResponseListActivity} in two-pane mode (on tablets) or a
 * {@link PollResponseDetailActivity} on handsets.
 */
public class PollResponseDetailFragment extends ListFragment {

	static final String TAG = "PollResponseDetailFragment";
	public List<WorxPollEventDetails> pollRowItems = new ArrayList<WorxPollEventDetails>();
	PollResponseDetailListAdapter mAdapter;
	String eventId; 

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String ARG_TITLE = "title";
	public static final String ARG_ORG = "org";
	public static final String TO_RESPOND_EVENT_LIST = "to_repond_event_list";
	
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PollResponseDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			eventId = getArguments().getString(ARG_ITEM_ID);
			fetchAllOptionsForEvent(eventId);
		}
	}

	/**
	 * This method returns all options for the corresponding event**/
	private void fetchAllOptionsForEvent(String eventId) {
		//Create a query on the event: Fetch all events for which the current user is invited
		ParseQuery<WorxPollEvent> iquery = ParseQuery.getQuery(WorxPollEvent.class);
		iquery.whereEqualTo("objectId", eventId);
		
		ParseQuery<WorxPollEventDetails> query = ParseQuery.getQuery(WorxPollEventDetails.class);
		query.whereMatchesQuery("parent", iquery);
		query.findInBackground(new FindCallback<WorxPollEventDetails>() {
			public void done(List<WorxPollEventDetails> list, ParseException e) {
				if (e == null) {
					pollRowItems = list;
					mAdapter.clear();
					mAdapter.addAll(pollRowItems);
					mAdapter.notifyDataSetChanged();
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
		
		View rootView = inflater.inflate(R.layout.fragment_poll_response_detail_list,
				container, false);
		
		//Setting the event title and the organizer's name above the list of options
		((TextView) rootView.findViewById(R.id.title_value_detail)).setText(getArguments().getString(ARG_TITLE));
		((TextView) rootView.findViewById(R.id.organizer_value_detail)).setText(getArguments().getString(ARG_ORG));
		
		Button button = (Button) rootView.findViewById(R.id.btn_submit);
		   button.setOnClickListener(new OnClickListener()
		   {
	         @Override
	         public void onClick(View v)
	         {
	        	 saveUserSelection();
	        	 sendPushNotificationToOrganizer();
	        	 Toast.makeText(getActivity(), "Your response has been recorded. \n Thank you!", Toast.LENGTH_LONG).show();
	        	 //Close screen and go back to HostActivity
	        	 startHostActivity();
	         }
		   }); 
		return rootView; 
	}
	
	/**
	 * This method saves the options user selected for the corresponding event**/
	private void saveUserSelection() {
		ArrayList<WorxPollEventDetails> selectionList = new ArrayList<WorxPollEventDetails>();
		
		WorxPollUserSelection userSelection = new WorxPollUserSelection();
		userSelection.setUser(ParseUser.getCurrentUser());
		userSelection.setEventId(eventId);
		
		for(WorxPollEventDetails e: pollRowItems) {
			if(e.isEventSelected()) {
				selectionList.add(e);
			}
		}
		
		userSelection.setEventUserSelection(selectionList);
		userSelection.saveEventually(new SaveCallback() {
			@Override
			public void done(ParseException e) { 
				if(e == null) {
					Log.i(TAG, "User Selection saved");
				}
				else 
					Log.e(TAG, "There has been an error while saving user selection");
			}
		});
	} 

	/*
	* Send a push notification to the Organizer
	*/
	private void sendPushNotificationToOrganizer() {
		//Add the current user to the Installation Table
		
		
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put("username", "alisha.msde@gmail.com");
		//Create our Installation query
		//TODO: Change the below email to the invitees
		ParseQuery pQuery = ParseInstallation.getQuery(); // <-- Installation query
		pQuery.whereEqualTo("username", "alisha.msde@gmail.com"); // <-- you'll probably want to target someone that's not the current user, so modify accordingly
		
		//Send Push Notification
		ParsePush parsePush = new ParsePush();
		parsePush.setQuery(pQuery);
		parsePush.setMessage("You are invited to a new Poll!");
		parsePush.sendInBackground();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mAdapter = new PollResponseDetailListAdapter(getActivity(), pollRowItems);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}
	
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	    }

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		onDateSelected(v, position, id);
	}
	
	public void onDateSelected(View v, int position, long id) {
		Toast.makeText(getActivity(), "Inside onDataSelected: Do checkbox", Toast.LENGTH_LONG).show();
	}

	/**
	 * Start HostActivity
	 */
	public void startHostActivity() {
		Intent intent = new Intent(getActivity(), HostActivity.class);
		 intent.putExtra("TAB_POSITION", 1);
		 startActivity(intent);
	}
}
