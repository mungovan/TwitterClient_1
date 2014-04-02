package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.style.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	ArrayList<Tweet> tweets;
	String maxId;
	
	private static final int REQUEST = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		maxId = "-1";
		setContentView(R.layout.activity_timeline);
		tweets = new ArrayList<Tweet>();
		lvTweets = (PullToRefreshListView)findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(adapter);
	
	 
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int offset, int totalItemsCount) {
	            LoadTweets(); 
	        }

	        });
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	// Get more data, append to start of teh tweet list
            	new GetDataTask().execute();

            }
        });
		

	}
	
	private void LoadTweets() {
	
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				adapter.addAll(tweets);
				maxId = Tweet.GetMinID(tweets);
			}
	}, maxId);		
		
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
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
		  MyTwitterApp.getRestClient().postNewTweet(new JsonHttpResponseHandler(){
				
				public void onSuccess(JSONArray jsonTweets){
					tweets = Tweet.fromJson(jsonTweets);
					adapter.addAll(tweets);
					maxId = Tweet.GetMinID(tweets);
				}
			}, tweetText);	

		  Tweet justSaved = new Tweet(tweetText, tweetDate, MyTwitterApp.getCurrentUser());
		  adapter.insert(justSaved, 0);
	  }
	}
	

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
	    protected void onPostExecute(String[] result) {
	        ((PullToRefreshListView) lvTweets).onRefreshComplete();
	        super.onPostExecute(result);
	    }

		@Override
		protected String[] doInBackground(Void... params) {
	        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                ;
            }
            return null;
		}
	}

}
