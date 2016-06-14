package com.alisha.model;

import java.util.HashMap;
import java.util.Map;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("EventDetails")
public class WorxPollEventDetails extends ParseObject {
	
	/**
	 * Empty Constructor
	 */
	public WorxPollEventDetails() {
	}

	public static Map<String, WorxPollEventDetails> EVENT_DETAIL_ITEM_MAP = new HashMap<String, WorxPollEventDetails>();

	
	public String getEventDate() {
		return getString("eventDate");
	}

	public void setEventDate(String eventDate) {
		put("eventDate",eventDate);
	}

	public String getEventStartTime() {
		return getString("eventStartTime");
	}

	public void setEventStartTime(String eventStartTime) {
		put("eventStartTime",eventStartTime);
	}

	public String getEventEndTime() {
		return getString("eventEndTime");
	}

	public void setEventEndTime (String eventEndTime) {
		put("eventEndTime",eventEndTime);
	}

	public boolean isEventSelected() {
		return getBoolean("isSelected");
	}

	public void setEventSelected(Boolean isSelected) {
		put("isSelected",isSelected);
	}	
}
