package com.alisha.polltiming;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.alisha.R;
import com.alisha.host.HostActivity;
import com.alisha.model.WorxPollConstants;
import com.alisha.model.WorxPollEvent;
import com.alisha.model.WorxPollEventDetails;
import com.alisha.model.WorxPollInvitations;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * @author alishaalam
 * 
 */
public class PollTimingFragment extends ListFragment {

	public static final String TAG = "PollTimingFragment";

	public ArrayList<String> receivedList;
	PollTimingAdapter mAdapter;
	public List<WorxPollEventDetails> mEventRowItems = new ArrayList<WorxPollEventDetails>();
	
	WorxPollEvent event = new WorxPollEvent();
	WorxPollInvitations invitedUser = new WorxPollInvitations();
	
	public static final int SEND_POLL = 1;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PollTimingFragment() {
	}

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	OnPollDetailSelectedListener mCallback;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface OnPollDetailSelectedListener {
		/**
		 * Callback for when an item has been selected
		 */
		public void onPollSelected(View v, int position, long id);
	}

	/**
	 * The Fragment captures the interface implementation during its onAttach()
	 * lifecycle method and can then call the Interface methods in order to
	 * communicate with the Activity.
	 **/
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("Alishaa - PollDetailFragmentList", "onAttach called");
		try {
			mCallback = (OnPollDetailSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnPollDetailSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Alishaa - PollDetailFragmentList", "onCreateView called");
		View view = inflater.inflate(R.layout.poll_timing_list_fragment,
				container, true);

		// Listen to sendPoll button click
		Button btn_send_poll = (Button) view.findViewById(R.id.btn_send_poll);
		btn_send_poll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Fetch all the dates and times the user has selected mRowItems
				 * contains all the dates and times selected by the user sp save
				 * the mRowItems to DB
				 */
				printRowItems();

				/*
				 * Save the fetched date and times to DB Check if NW is
				 * available If nw is not available save the data store to DB
				 * when nw connectivity is restored
				 */
				try {
					saveEventDetailsToParse();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendPushNotificationToUser();
				

				//sharePoll();
				String url = "http://www.worxpoll.com#q=optionsList";
				// Call WorxMail to send invites
				final Intent intent = new Intent(Intent.ACTION_SEND, null);
				
				intent.putExtra(Intent.EXTRA_SUBJECT, "[Propose Time] WorxPoll Demo");
				intent.putExtra(Intent.EXTRA_TEXT, url);
				intent.setType("text/plain");

				// Verify it resolves
				PackageManager packageManager = getActivity()
						.getPackageManager();
				List<ResolveInfo> activities = packageManager
						.queryIntentActivities(intent, 0);
				boolean isIntentSafe = activities.size() > 0;

				// Start an activity if it's safe
				if (isIntentSafe) {
					startActivityForResult(intent, SEND_POLL);
				}
			}
		});

		return view;
	}
	
	private void sendPushNotificationToUser() {
		//Create our Installation query
		ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery(); // <-- Installation query
		pQuery.whereEqualTo("username", "alisha.msde@gmail.com"); // <-- you'll probably want to target someone that's not the current user, so modify accordingly
		
		//Send Push Notification
		ParsePush parsePush = new ParsePush();
		parsePush.setQuery(pQuery);
		parsePush.setMessage("You are invited to a new Poll!");
		parsePush.sendInBackground();	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Success for worxmail, mail was sent, start hostactivity
		if(requestCode == SEND_POLL && resultCode == -1) { 
			Intent intent = new Intent(getActivity(), HostActivity.class);
			startActivity(intent);
			//TODO: Get back the list of recipients
			
		} else if(requestCode == SEND_POLL && resultCode == -1) {
			//User cancelled his operation
			getActivity().finish();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WorxPollEventDetails eventItem;
		receivedList = ((PollTimingActivity)getActivity()).getDateList();
		Log.d("Alishaa-frg", "Datelist size" + receivedList.size());

		for (String s : receivedList) {
			//TODO: Adding the foll lines to add Parse DB
			eventItem = new WorxPollEventDetails();
			eventItem.setEventDate(s);
			eventItem.setEventStartTime("10");
			eventItem.setEventEndTime("11");
			mEventRowItems.add(eventItem);
		}
		mAdapter = new PollTimingAdapter(getActivity(), mEventRowItems);
		setListAdapter(mAdapter);
	}

	/**
	 * The following method in the fragment is called when the user clicks on a
	 * list item. The fragment uses the callback interface to deliver the event
	 * to the parent activity.
	 **/
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Send the event to the host activity
		mCallback.onPollSelected(v, position, id);
	}

	/**
	 * Prints all the elements in mRowItems
	 */
	public void printRowItems() {
		// Printing rowItems
		for (WorxPollEventDetails item : mEventRowItems) {
			Log.i(TAG,
					item.getEventDate() + " "
							+ item.getEventStartTime() + " "
							+ item.getEventEndTime());
		}
	}

	/**
	 * Save mRowItems to DB
	 * @throws ParseException 
	 */
	public void saveEventDetailsToParse() throws ParseException {
		
		//Retrieve values from Shared Preferences
		SharedPreferences sharedPref = getActivity().getSharedPreferences("WorxPollPrefsFile", 0);
		String eventTitle = sharedPref.getString(WorxPollConstants.EVENTTITLE, "");
		String eventLocation = sharedPref.getString(WorxPollConstants.EVENTLOCATION, "");
		String organizerEmail = ParseUser.getCurrentUser().getUsername();
		
		event.setEventTitle(eventTitle);
		Log.d(TAG, "eventTitle: " + eventTitle);
		event.setEventLocation(eventLocation);
		event.setEventOrgEmailId(organizerEmail);
		Log.d(TAG, "organizerEmail: " + organizerEmail); 
		
		//TODO: The following ParseUser will be the users who are invited to the Poll
		invitedUser.setUserEmail("alisha.alam2012@gmail.com");
		invitedUser.save();
		
		/*Adding ParseUser as a Relation to Event Table
		Using RELATIONS for Invitees as its a many to many relation
		https://parse.com/questions/pointer-vs-relation */
		ParseRelation<WorxPollInvitations> eventRelation = event.getRelation("invitees");
		eventRelation.add(invitedUser);
		
		/*Linking Event to EventDetails
		Using POINTERS for EventDetails as its a one-many relationship
		https://parse.com/questions/pointer-vs-relation */
		for (WorxPollEventDetails eventDetails : mEventRowItems) {
			eventDetails.put("parent", event);
			eventDetails.save();
			//TODO: Uncomment the callback code
			/*(new SaveCallback() { 
			    @Override
			    public void done(ParseException e) { 
			    	Toast.makeText(getActivity(), "Data saved?", Toast.LENGTH_LONG).show();
			    } 
			  });*/
		}
	}
}