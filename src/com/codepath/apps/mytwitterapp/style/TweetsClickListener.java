package com.codepath.apps.mytwitterapp.style;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.apps.mytwitterapp.Configuration;
import com.codepath.apps.mytwitterapp.ProfileActivity;
import com.codepath.apps.mytwitterapp.models.Tweet;


public class TweetsClickListener implements OnItemClickListener {
	private ArrayList<Tweet> tweets;

	public TweetsClickListener(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(view.getContext(), ProfileActivity.class);
		Tweet tweet = tweets.get(position);
		i.putExtra(Configuration.USER_ID, tweet.getUser().getId());
		view.getContext().startActivity(i);

	}

}