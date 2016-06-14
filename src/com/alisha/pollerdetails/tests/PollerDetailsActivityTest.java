package com.alisha.pollerdetails.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.alisha.R;
import com.alisha.pollerdetails.PollerDetailsActivity;

public class PollerDetailsActivityTest extends ActivityInstrumentationTestCase2<PollerDetailsActivity>{

	private PollerDetailsActivity mPollerDetailsActivity;
	private TextView mTextView;
	
	public PollerDetailsActivityTest() {
		super(PollerDetailsActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mPollerDetailsActivity = getActivity();
        mTextView =
                (TextView) mPollerDetailsActivity
                .findViewById(R.id.textViewTitle);
    }
	
	public void testPreconditions() {
	    assertNotNull("PollerDetailsActivityTest is null", mPollerDetailsActivity);
	    assertNotNull("mTextView is null", mTextView);
	}

	public void testMyFirstTestTextView_labelText() {
	    final String expected =
	    		mPollerDetailsActivity.getString(R.string.event_title);
	    final String actual = mTextView.getText().toString();
	    assertEquals(expected, actual);
	}
}
