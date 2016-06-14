package com.alisha.model;

import java.util.ArrayList;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("UserSelection")
public class WorxPollUserSelection extends ParseObject{ 
	
	public WorxPollUserSelection(){
		
	}
	
	public String getEventId() {
		return getString("eventId");
	}
	
	public void setEventId(String eventId) {
		put("eventId", eventId);
	}
	
	public String getParseUser() {
		return getString("participant");
	}
	
	public void setUser(ParseUser user) {
		put("participant", user);
	}

	public void setEventUserSelection(ArrayList<WorxPollEventDetails> selection) {
		put("selectionList", selection);
	}

	public void getEventUserSelection(){
		getList("selectionList");
	}

	
}
