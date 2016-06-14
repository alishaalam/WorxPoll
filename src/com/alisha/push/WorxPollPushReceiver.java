package com.alisha.push;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alisha.host.HostActivity;
import com.parse.ParsePushBroadcastReceiver;

public class WorxPollPushReceiver extends ParsePushBroadcastReceiver {
	
	private final String TAG = WorxPollPushReceiver.class.getSimpleName();
    private Intent parseIntent;
    
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
 
        if (intent == null)
            return;
 
        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
 
            Log.e(TAG, "Push received: " + json);
 
            parseIntent = intent;
 
            parsePushJson(context, json);
 
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }
 
    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }
 
    @Override
    protected void onPushOpen(Context context, Intent intent) {
        //super.onPushOpen(context, intent);
        	Intent newIntent = new Intent(context, HostActivity.class);
        	newIntent.putExtra("TAB_POSITION", 1);
        	newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       	 	context.startActivity(newIntent);
    }
    
    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
 
            if (!isBackground) {
            	Intent newIntent = new Intent(context, HostActivity.class);
            	newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	newIntent.putExtra("TAB_POSITION", 1);
           	 	context.startActivity(newIntent);
            }
 
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

}
