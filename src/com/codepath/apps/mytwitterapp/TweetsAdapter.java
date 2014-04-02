package com.codepath.apps.mytwitterapp;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tweet tweet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tweet_item, null);
        }
        
        // Lookup views within item layout
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivProfile);
        ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
        TextView nameView = (TextView) convertView.findViewById(R.id.tvName);
        TextView dateView = (TextView) convertView.findViewById(R.id.tvDate);
        
        String formattedName = "<b>" + tweet.getUser().getScreenName() + "</b>" + " <small><font color='#7777777'>@" +
        		tweet.getUser().getScreenName() + "</font></small>";
        
        String formattedDate = "<small><font color='#7777777'>" +
        		 tweet.GetRelativeDate() + "</font></small>";
  
        // Populate the data into the template view using the data object
        nameView.setText(Html.fromHtml(formattedName));
        dateView.setText(Html.fromHtml(formattedDate));
         
        TextView bodyView = (TextView)convertView.findViewById(R.id.tvBody);
        bodyView.setText(Html.fromHtml(tweet.getBody()));
        
        return convertView;
    }
	

}
