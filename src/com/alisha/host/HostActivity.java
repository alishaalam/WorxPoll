package com.alisha.host;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alisha.R;
import com.alisha.dispatch.LoginDispatchActivity;
import com.alisha.dispatch.ParseApplicationHelper;
import com.alisha.pollerdetails.PollerDetailsActivity;
import com.alisha.settings.SettingsActivity;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class HostActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	public ViewPager mViewPager;
	ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host);
		//Initialize Parse // DONT DELETE
		ParseApplicationHelper parseHelper = new ParseApplicationHelper();

		//Add the current user to the Installation Table
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put("username", ParseUser.getCurrentUser().getUsername());
		installation.saveEventually();
		
		//To initialize the Preferences when the app starts
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
		//To put a listener to get notified when the preferences change
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.OnSharedPreferenceChangeListener listener =
			    new SharedPreferences.OnSharedPreferenceChangeListener() {
			  public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			    // listener implementation
			  }
			};
		prefs.registerOnSharedPreferenceChangeListener(listener);

		// Set up the action bar.
		mActionBar = getActionBar();

		// Setup the navigation Mode
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Get a reference to the viewPager from the host_activity layout
		mViewPager = (ViewPager) findViewById(R.id.pager);

		/** Defining a listener for pageChange */
        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        };
 
        /** Setting the pageChange listner to the viewPager */
        mViewPager.setOnPageChangeListener(pageChangeListener);

        
        /** Create the FragmentPagerAdapter that will return a fragment for each of the three
        		 primary sections of the activity.  **/
        FragmentManager fm = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(fm);
 
        /** Setting the FragmentPagerAdapter object to the viewPager object */
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        /** Defining tab listener */
    	ActionBar.TabListener tabListener = new ActionBar.TabListener() {

    		@Override
    		public void onTabReselected(Tab arg0,
    				android.app.FragmentTransaction arg1) {
    			// TODO Auto-generated method stub
    		}

    		@Override
    		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
    			mViewPager.setCurrentItem(tab.getPosition());
    			//ft.replace(R.id.pager, fragment);
    		}

    		@Override
    		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
    			// TODO Auto-generated method stub
    		}
    	};

		// Declaring our tabs
		Tab createdPollTab = mActionBar.newTab().setText("Polls you Created")
				.setTabListener(tabListener);
		Tab yetToRespondPollTab = mActionBar.newTab().setText("Polls To Respond")
				.setTabListener(tabListener);
		/*Tab alreadyRespondPollTab = mActionBar.newTab().setText("Polls Already Responded")
				.setTabListener(tabListener);
*/
		// Add the tab to the ActionBar
		mActionBar.addTab(createdPollTab);
		mActionBar.addTab(yetToRespondPollTab);
		//mActionBar.addTab(alreadyRespondPollTab);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		int tabPosition = getIntent().getIntExtra("TAB_POSITION", 0);
		mViewPager.setCurrentItem(tabPosition);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}
	
	@Override
	public void setIntent(Intent newIntent) {
		// TODO Auto-generated method stub
		super.setIntent(newIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_poll:
	            createPoll();
	            return true;
	        case R.id.action_settings:
	            openSettings(); 
	            return true;
	        case R.id.action_logout:
	            logoutCurrentUser(); 
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	//Create a poll from the menu item
	private void createPoll() {
		Intent intent = new Intent(this, PollerDetailsActivity.class);
		startActivity(intent);
	}
	
	//Open the settings activity
	private void openSettings(){
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void logoutCurrentUser() {
	        ParseUser.logOut();

	        // FLAG_ACTIVITY_CLEAR_TASK only works on API 11, so if the user
	        // logs out on older devices, we'll just exit.
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	          Intent intent = new Intent(HostActivity.this,
	              LoginDispatchActivity.class);
	          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
	              | Intent.FLAG_ACTIVITY_NEW_TASK);
	          startActivity(intent);
	        } else {
	          finish();
	        }
	}
	
}
