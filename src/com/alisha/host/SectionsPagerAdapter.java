package com.alisha.host;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.alisha.organizerview.PollReplyListFragment;
import com.alisha.pollparticipant.PollResponseListFragment;

/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	
    	final int PAGE_COUNT = 2;

    	Context ctx;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
           
        	Bundle data = new Bundle();
            switch(position){
                /** Poll you Created tab(OrganizerView) is selected */
                case 0:
                	PollReplyListFragment pollReplyFragment = new PollReplyListFragment();
                    data.putInt("current_page", position+1);
                    pollReplyFragment.setArguments(data);
                    return pollReplyFragment;
                /** PollResponseListFragment tab is selected **/	
                case 1:
                	PollResponseListFragment pollResponseFragment = new PollResponseListFragment();
                    data.putInt("current_page", position+1);
                    pollResponseFragment.setArguments(data);
                    return pollResponseFragment;    
             /*   case 2:
                	PollResponseListFragment pollRespondedFragment = new PollResponseListFragment();
                    data.putInt("current_page", position+1);
                    pollRespondedFragment.setArguments(data);
                    return pollRespondedFragment;*/
                default: 
                	Toast.makeText(ctx, "You are in the default section. Check your getItem()", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
            // TODO externalize the strings
                /*case 0:
                    return ctx.getResources().getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return ctx.getResources().getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return ctx.getResources().getString(R.string.title_section3).toUpperCase(l);*/
            }
            return null;
        }
    }