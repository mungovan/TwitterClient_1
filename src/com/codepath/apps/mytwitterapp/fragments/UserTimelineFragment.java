package com.codepath.apps.mytwitterapp.fragments;

import com.codepath.apps.mytwitterapp.Configuration;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {
	
	long userId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected String getDomain() {
		return Configuration.USER_TIMELINE + "?user_id=" +  String.valueOf(userId);
	}
	
	public void setupFragment(long uid) {
		Bundle args = new Bundle();
		args.putLong(Configuration.USER_ID, uid);
		userId = uid;
		this.setArguments(args);
		
	}

}
