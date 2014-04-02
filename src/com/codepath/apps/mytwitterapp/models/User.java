package com.codepath.apps.mytwitterapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	private long id;
	private String screenName;
	private String profileImageUrl;
	
	
	public String getProfileImageUrl(){
		return profileImageUrl;
	}
	
	public String getScreenName(){
		return screenName;
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
            
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return u;
    }

}
