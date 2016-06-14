package com.alisha.model;

import java.util.HashMap;
import java.util.Map;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Events")
public class WorxPollEvent extends ParseObject {
	
	public static Map<String, WorxPollEvent> EVENT_ITEM_MAP = new HashMap<String, WorxPollEvent>();
	
	
	public WorxPollEvent() {	
	}
	
	public String getEventTitle() {
		return getString("eventTitle");
	}
	public void setEventTitle(String eventTitle) {
		put("eventTitle", eventTitle);
	}
	public String getEventLocation() {
		return getString("eventLocation");
	}
	public void setEventLocation(String eventLocation) {
		put("eventLocation", eventLocation);
	}
	
	public String getEventOrganizer() {
		return getString("eventOrganizer");
	}
	public void setEventOrganizer(String eventOrganizer) {
		put("eventOrganizer", eventOrganizer);
	}
	public String getEventOrgEmailId() {
		return getString("eventOrgEmailId");
	}
	public void setEventOrgEmailId(String eventOrgEmailId) {
		put("eventOrgEmailId", eventOrgEmailId);
	}
	
	
}
