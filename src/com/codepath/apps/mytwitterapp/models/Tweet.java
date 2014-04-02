package com.codepath.apps.mytwitterapp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
    private String text;
    private String body;
    private long id;
    private User user;
    private boolean isFavorited;
    private boolean isRetwetted;
    private String date;


    public Tweet(String body, String date, User user){
    	// I set both of these here as I wasn't sure if the body or text was the tweet characters.
    	this.body = body;
    	this.text = body;
    	this.user = user;
    	this.date = date;
    	
    }
    
    public Tweet(){
    	
    }
    
    public String getText() {
        return text;
    }

    public String getBody() {
        return body;
    }
    
    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public boolean getFavorited() {
        return isFavorited;
    }

    public boolean getRetwetted() {
        return isRetwetted;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
    	Tweet t = new Tweet();
        try {
            // Deserialize json into object fields
            t.text = jsonObject.getString("text");
            t.id = jsonObject.getLong("id");
            t.user = User.fromJson(jsonObject.getJSONObject("user"));
            t.isFavorited = jsonObject.getBoolean("favorited");
            t.isRetwetted =  jsonObject.getBoolean("retweeted");
            t.body = jsonObject.getString("text");
            t.date = jsonObject.getString("created_at");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return t;
    }
    
    
    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                 tweets.add(tweet);
            }
        }

        return tweets;
    }
    
    
    public String GetRelativeDate(){

    		  final String TWITTER ="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    		  SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
    		  sf.setLenient(true);
    		  Date d = null;
			try {
				d = sf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		  Date now = new Date();
    		  
    		  return TimeUtils.millisToLongDHMS(now.getTime() - d.getTime());
    	
    }
    
    
	public static String GetMinID(ArrayList<Tweet> tweets) {
		if(tweets.size() < 1)
			return "-1";
		long min = tweets.get(0).id;
		for(Tweet t : tweets){
			if(t.id < min){
				min = t.id;
			}
		}
		return String.valueOf(min-1);
	}

    
    
}
