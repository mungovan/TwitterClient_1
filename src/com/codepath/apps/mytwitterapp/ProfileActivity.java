package com.codepath.apps.mytwitterapp;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.fragments.UserTimelineFragment;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity  extends FragmentActivity{

	private User user;
	private long uid;
	private TextView tvUserName;
	private TextView tvDescription;
	private TextView tvNumFollowers;
	private TextView tvNumFollowing;
	private ImageView ivTimelineProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvDescription = (TextView) findViewById(R.id.tvDesciption);
		tvNumFollowers = (TextView) findViewById(R.id.tvNumFollowers);
		tvNumFollowing = (TextView) findViewById(R.id.tvNumFollowing);
		ivTimelineProfile = (ImageView) findViewById(R.id.ivUserProfile);

		loadProfileInfo();
		populateTimeline();

	}

	
	public void loadProfileInfo() {

		uid = (long) getIntent().getLongExtra(Configuration.USER_ID, 0);
		MyTwitterApp.getRestClient().getUserInfoById(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonUserArray) {
				try {
					user = User.fromJson(jsonUserArray.getJSONObject(0));
					getActionBar().setTitle("@"+user.getScreenName());
					tvUserName.setText(user.getScreenName());
					tvNumFollowers.setText(user.getFollowersCount() + " Followers");
					tvNumFollowing.setText(user.getFollowingCount() + " Following");
					String desc = user.getDescription();
					if(!desc.isEmpty() && desc.length() > 70){
						tvDescription.setText(desc.substring(0, 70) + "...");
					}
					ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivTimelineProfile);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}, uid);
	}

	public void populateTimeline(){
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		
		UserTimelineFragment userTimelineFragment = new UserTimelineFragment();	
		userTimelineFragment.setupFragment(this.uid);		
		fts.replace(R.id.frameTimeline, userTimelineFragment);
		fts.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
}