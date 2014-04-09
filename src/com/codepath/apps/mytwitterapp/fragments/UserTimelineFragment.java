package com.codepath.apps.mytwitterapp.fragments;

import com.codepath.apps.mytwitterapp.Configuration;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected String getDomain() {
		return Configuration.USER_TIMELINE;
	}
	


	public void setupFragment(long uid) {
		Bundle args = new Bundle();
		args.putLong(Configuration.USER_ID, uid);
		this.setArguments(args);
		
	}


}
