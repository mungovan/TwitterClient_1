package com.codepath.apps.mytwitterapp.fragments;

import com.codepath.apps.mytwitterapp.Configuration;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	protected String getDomain() {
		return Configuration.HOME_TIMELINE;
	}
	
}
