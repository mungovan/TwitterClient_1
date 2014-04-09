package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.TwitterClient;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.style.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.style.TweetsClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

public abstract class TweetsListFragment extends Fragment {

	//PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	ArrayList<Tweet> tweets;
	String maxId;
	TwitterClient twitterClient;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragments_tweets_list, parent, false);		
		
		maxId = "-1";
		tweets = new ArrayList<Tweet>();
		PullToRefreshListView lvTweets = (PullToRefreshListView)view.findViewById(R.id.lvTweetsList);
		adapter = new TweetsAdapter(view.getContext(), tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnItemClickListener(new TweetsClickListener(tweets));
		
		twitterClient = MyTwitterApp.getRestClient();
		if(maxId == null)
			maxId = "-1";
		twitterClient.getTweets(new JsonHttpResponseHandler () {
			@Override
			public void onSuccess(JSONArray jsonTweets) {

				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);

				getAdapter().addAll(tweets);
				PullToRefreshListView lvTweets = (PullToRefreshListView)getActivity().findViewById(R.id.lvTweetsList);
				lvTweets.setOnScrollListener(new EndlessScrollListener() {

					@Override
					public void onLoadMore(int page, int totalItemsCount) {
						LoadTweets();					
					}			    
			    });
			}

		}, maxId, getDomain());
		
		return view;
	}
	
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

				
	}
	
	
	protected abstract String getDomain();
	
	
	private void LoadTweets(){
		MyTwitterApp.getRestClient().getTweets(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				adapter.addAll(tweets);
				maxId = Tweet.GetMinID(tweets);
			}
		}, maxId, getDomain());	
	}

	public class GetDataTask extends AsyncTask<Void, Void, String[]> {

		// Didn't get to implement this pull for more functionality
		@Override
	    protected void onPostExecute(String[] result) {
	        //((PullToRefreshListView) lvTweets).onRefreshComplete();
	        super.onPostExecute(result);
	    }

		@Override
		protected String[] doInBackground(Void... params) {
	        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {               
            }
            return null;
		}
	}
	
	
	public void AddNewTweet(String date, String text)
	{	
	  MyTwitterApp.getRestClient().postNewTweet(new JsonHttpResponseHandler(){
			
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				adapter.addAll(tweets);
				maxId = Tweet.GetMinID(tweets);
			}
		}, text);	

	  Tweet justSaved = new Tweet(text, date, MyTwitterApp.getCurrentUser());
	  adapter.insert(justSaved, 0);
	}
	
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
	
	
}
