package com.alisha.dispatch;

import android.app.Application;
import android.util.Log;

import com.alisha.model.WorxPollEvent;
import com.alisha.model.WorxPollEventDetails;
import com.alisha.model.WorxPollInvitations;
import com.alisha.model.WorxPollUserSelection;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ParseApplicationHelper extends Application {
	
	private static final String TAG = "ParseHelper";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Initializing ParseHelper");
		/** 
		 * Initialise Parse 
		 */
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		ParseObject.registerSubclass(WorxPollEvent.class);
		ParseObject.registerSubclass(WorxPollEventDetails.class);
		ParseObject.registerSubclass(WorxPollInvitations.class);
		ParseObject.registerSubclass(WorxPollUserSelection.class);
		Parse.initialize(this, "QcGl8um2FDuM0r3Dom1clx94EoUbYLHzhZaqMtA8", "XIB0ZXPlOEEFP3HspXhDnSAEvuXm1ur6CKWzmpfx");
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.saveInBackground();

		
		ParsePush.subscribeInBackground("", new SaveCallback() {
			  @Override
			  public void done(ParseException e) {
			    if (e == null) {
			      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
			    } else {
			      Log.e("com.parse.push", "failed to subscribe for push", e);
			    }
			  }
			});
	}

}
