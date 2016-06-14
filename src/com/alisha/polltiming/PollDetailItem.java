package com.alisha.polltiming;

import java.util.HashMap;
import java.util.Map;

public class PollDetailItem {

	public String id;
	private String title;
	private String organizer;
	private String eventDate;
	private String eventStartTime;
	private String eventEndTime;
	private boolean isSelected;
	private int invitees;
	private int respondents;
	
	public PollDetailItem(String id, String title, String organizer, String eventDate,
			String eventStartTime, String eventEndTime, boolean isSelected) {
		super();
		this.id = id;
		this.title = title;
		this.organizer = organizer;
		this.eventDate = eventDate;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.isSelected = isSelected;
	}
	
	/**
	 * 
	 */
	public PollDetailItem() {
	}



	public static Map<String, PollDetailItem> POLL_ITEM_MAP = new HashMap<String, PollDetailItem>();
	
	/**
	 * @param eventDate
	 * @param eventStartTime
	 * @param eventEndTime
	 */
	public PollDetailItem(String id, String eventDate, String eventStartTime,
			String eventEndTime, boolean isSelected) {
		super();
		this.id = id;
		this.eventDate = eventDate;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.isSelected = isSelected;
	}


	/**
	 * @param id
	 * @param eventDate
	 * @param eventStartTime
	 * @param eventEndTime
	 */
	public PollDetailItem(String id, String eventDate, String eventStartTime,
			String eventEndTime) {
		super();
		this.id = id;
		this.eventDate = eventDate;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
	}

	/**
	 * @param title
	 * @param organizer
	 */
	public PollDetailItem(String id, String title, String organizer) {
		this.id = id;
		this.title = title;
		this.organizer = organizer;
	}
	
	/**
	 * @param id
	 * @param eventDate
	 * @param eventStartTime
	 * @param eventEndTime
	 * @param invitees
	 */
	public PollDetailItem(String id, String eventDate, String eventStartTime,
			String eventEndTime, int invitees) {
		super();
		this.id = id;
		this.eventDate = eventDate;
		this.eventStartTime = eventStartTime;
		this.eventEndTime = eventEndTime;
		this.invitees = invitees;
	}
	
	

	/**
	 * @param id
	 * @param title
	 * @param invitees
	 * @param respondents
	 */
	public PollDetailItem(String id, String title,
			int invitees, int respondents) {
		super();
		this.id = id;
		this.title = title;
		this.invitees = invitees;
		this.respondents = respondents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	
	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public String getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getInvitees() {
		return invitees;
	}

	public void setInvitees(int invitees) {
		this.invitees = invitees;
	}

	
	public int getRespondents() {
		return respondents;
	}

	public void setRespondents(int respondents) {
		this.respondents = respondents;
	}

	@Override
	public String toString() {
		return String
				.format("PollDetailItem [id=%s, title=%s, organizer=%s, eventDate=%s, eventStartTime=%s, eventEndTime=%s, isSelected=%s, invitees=%s]",
						id, title, organizer, eventDate, eventStartTime,
						eventEndTime, isSelected, invitees);
	}
	
}
