package com.alisha.pollparticipant;


import android.app.Activity;
import android.os.Bundle;

import com.alisha.R;


/**
 * An activity representing a single Poll detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PollResponseListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link PollResponseDetailFragment}.
 */
public class PollResponseDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_response_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PollResponseDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(PollResponseDetailFragment.ARG_ITEM_ID));
            PollResponseDetailFragment fragment = new PollResponseDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.poll_list_detail, fragment)
                    .commit();
        }
    }	
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    }
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    }
}
