package com.alisha.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Invitations")
public class WorxPollInvitations extends ParseObject{	

	public WorxPollInvitations() {
	}

	

	public String getUserEmail() {
		return getString("userEmail");
	}

	public void setUserEmail(String userEmail) {
		put("userEmail",userEmail);
	}

}
