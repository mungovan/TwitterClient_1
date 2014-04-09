package com.codepath.apps.mytwitterapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;
import com.codepath.apps.mytwitterapp.fragments.TweetsListFragment;

public class TimelineActivity extends FragmentActivity implements TabListener {
	
	TweetsListFragment fragmentTweets;
	
	private static final int REQUEST = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_timeline);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		setupNavigationTabs();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposePress(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQUEST);
	}
	
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(Configuration.USER_ID, MyTwitterApp.getCurrentUser().getId());
		startActivity(i);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST) {

		  String tweetText =  data.getExtras().getString("tweet_text");
		  String tweetDate =  data.getExtras().getString("tweet_date");
		  
          if (!tweetText.isEmpty() && !tweetDate.isEmpty() && fragmentTweets != null) {
              fragmentTweets.AddNewTweet(tweetDate, tweetText);
          }
	
	  }
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar= getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome= actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
				.setTabListener(this);
		
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment").setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			fragmentTweets = new HomeTimelineFragment();
		} 
		else {
			fragmentTweets = new MentionsFragment();
		}
	       fts.replace(R.id.frame_container, fragmentTweets);
	       fts.commit();	
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


}
