package com.codepath.apps.mytwitterapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	private long id;
	private String screenName;
	private String profileImageUrl;
	private int numFollowing;
	private int numFollowers;
	private String description;
	
	
	public String getProfileImageUrl(){
		return profileImageUrl;
	}
	
	public String getScreenName(){
		return screenName;
	}
	
	 public int getFollowersCount() {
	        return this.numFollowers;
	    }

	 public int getFollowingCount() {
	        return this.numFollowing;
	    }
	 
	 public String getDescription(){
		 return description;
	 }
	    
	public long getId(){
		return id;
	}
	
    public static User fromJson(JSONObject jsonObject) {
    	User u = new User();
        try {
            // Deserialize json into object fields
            u.id = jsonObject.getLong("id");
            u.screenName = jsonObject.getString("screen_name");
            u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.numFollowers = jsonObject.getInt("followers_count");
            u.numFollowing = jsonObject.getInt("friends_count");
            u.description = jsonObject.getString("description");
            
            
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return u;
    }

}
