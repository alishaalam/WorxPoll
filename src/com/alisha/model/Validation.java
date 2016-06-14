package com.alisha.model;

import java.util.regex.Pattern;

import android.widget.EditText;

public class Validation {

	// Regular Expression
	//No REGEX FOR TITLE, LOCATION SO JUST TEST BLANK OR NOT
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String NAME_REGEX = "[a-zA-Z]+[\\s][a-zA-Z]+";
	private static final String PHONE_REGEX = "\\d{10}";
	private static final String TIME_REGEX = "\\d{2}";

	// Error Messages
	private static final String REQUIRED_MSG = "Required";
	private static final String EMAIL_MSG = "Enter a valid email address ";
	private static final String NAME_MSG = "Enter your first and last name";
	private static final String DATE_REGEX = "(0[0-9]|1[0-2])/[0-9][0-9]";
	private static final String TITLE_MSG = "Enter a title for your poll";
	private static final String LOCATION_MSG = "Enter a location for your event";
	private static final String DATE_MSG = "Enter Month & Year XX/XX";
	private static final String PHONE_MSG = "Enter a 10 digit phone number";
	private static final String TIME_MSG = "Enter a valid time";
	
	// call to check email
	public static boolean isValidEmailAddress(EditText editText, boolean required) {
		return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
	}


	//call to check name
	public static boolean isValidPersonName(EditText editText, boolean required)
	{
		return isValid(editText, NAME_REGEX, NAME_MSG, required);
	}
	
	public static boolean isValidDate(EditText editText, boolean required)
	{
		return isValid(editText,DATE_REGEX,DATE_MSG,required);
	}
	
	public static boolean isValidTitle(EditText editText, boolean required)
	{
		return isValid(editText,null,TITLE_MSG,required);
	}
	
	public static boolean isValidLocation(EditText editText, boolean required)
	{
		return isValid(editText,null,LOCATION_MSG,required);
	}
	
	public static boolean isValidTime(EditText editText, boolean required)
	{
		return isValid(editText,TIME_REGEX,TIME_MSG,required);
	}

	// return true if the input field is valid, based on the parameter passed
	private static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

		String text = editText.getText().toString().trim();
		
		//Phone is special case must remove all formatting
		if(regex!=null && regex.equals(PHONE_REGEX)) {
			text=RemoveFormattingPhoneNumber(text);
		}
		// clearing the error, if it was previously set by some other values
		editText.setError(null);

		// text required and editText is blank, so return falses
		if ( required && !hasText(editText) ) return false;

		// pattern doesn't match so returning false
		if(regex==null) {
			if(editText.getText().toString().equals("")) {
				editText.setError(errMsg);
				return false;
			}
		}
		else if (required && !Pattern.matches(regex, text)) {
			editText.setError(errMsg);
			return false;
		}

		return true;
	}

	// check the input field has any text or not
	// return true if it contains text otherwise false
	public static boolean hasText(EditText editText) {

		String text = editText.getText().toString().trim();
		editText.setError(null);

		// length 0 means there is no text
		if (text.length() == 0) {
			editText.setError(REQUIRED_MSG);
			return false;
		}
		return true;
	}
	
	public static String AddFormattingPhoneNumber(String phoneNum)
	{
		if(phoneNum.length()<3)
		{
			return phoneNum;
		}
		else if(phoneNum.length()<6)
		{
			return "("+phoneNum.substring(0,3)+") "+phoneNum.substring(3);
		}
		else if(phoneNum.length()<10)
		{
			return "("+phoneNum.substring(0,3)+") "+phoneNum.substring(3,6)+"-"+phoneNum.substring(6);
		}
		else
			return "("+phoneNum.substring(0,3)+") "+phoneNum.substring(3,6)+"-"+phoneNum.substring(6, 10);
	}
	
	public static String RemoveFormattingPhoneNumber(String phoneNum)
	{
		return phoneNum.replaceAll("\\D+", "");
	}
	public static String ResetFormattingPhoneNumber(String phoneNum)
	{
		return AddFormattingPhoneNumber(phoneNum.replaceAll("\\D+", ""));
	}
	
}