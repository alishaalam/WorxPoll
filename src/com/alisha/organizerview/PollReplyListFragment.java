package com.alisha.organizerview;

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
import com.alisha.polltiming.PollDetailItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * A list fragment representing a list of PollReplies. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link PollReplyDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link PollSelected}
 * interface.
 */
public class PollReplyListFragment extends ListFragment {
	
	public static List<PollDetailItem> rowItems = new ArrayList<PollDetailItem>();;
	static final String TAG = "PollReplyListFragment";
	PollReplyListAdapter adapter;

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
    public PollReplyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Fetch events data from Parse
        fetchEventsForOrganizerFromParse();
    }
    
    private void fetchEventsForOrganizerFromParse() {
    	ParseQuery<WorxPollEvent> query = ParseQuery.getQuery(WorxPollEvent.class);
		query.whereEqualTo("eventOrgEmailId", ParseUser.getCurrentUser().getUsername());
		query.findInBackground(new FindCallback<WorxPollEvent>() {
		    public void done(List<WorxPollEvent> eventList, ParseException e) {
		        if (e == null) {
		            Log.d(TAG, "Retrieved " + eventList.size());
		            int i=0;
		            for (WorxPollEvent event : eventList) {
		            	Log.d(TAG, "Title: " + event.getEventTitle());
		            	PollDetailItem item = new PollDetailItem(Integer.toString(i), event.getEventTitle(), i+20, i+12);
		        		rowItems.add(item);
		        		PollDetailItem.POLL_ITEM_MAP.put(item.id, item);
		        		i++;
		            }
		            adapter.notifyDataSetChanged();
		        } else {
		            Log.d(TAG, "Error: " + e.getMessage());
		        }
		    }
		});
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_pollreply_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
		
    	adapter = new PollReplyListAdapter(getActivity(), rowItems);
    	setListAdapter(adapter);
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
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        onItemSelected(rowItems.get(position).id);
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
        getListView().setChoiceMode(activateOnItemClick
                ? AbsListView.CHOICE_MODE_SINGLE
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
    
    public void onItemSelected(String id) {
		// Start the detail activity for the selected item ID.
		Intent chartIntent = new Intent(getActivity(),
				HorizontalBarChartActivity.class);
		startActivity(chartIntent);
    }    
}
