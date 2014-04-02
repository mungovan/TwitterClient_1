package com.codepath.apps.mytwitterapp;

import org.scribe.builder.api.*;

import android.content.Context;

import com.codepath.apps.mytwitterapp.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "ikn3NR3zQ07Uf0UjlhBWEb1nK";       // Change this
    public static final String REST_CONSUMER_SECRET = "Dw1ZjV9hu7hZLd4twjG81zTBK5QwUcDNhzWU27xOOXrLyYwfXz"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterappcodepathtweets"; // Change this (here and in manifest)
    public static final int NUMBER_TWEETS = 25;
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, String maxId){
    	
    	String url = getApiUrl("statuses/home_timeline.json");

    	RequestParams params = new RequestParams();
    	params.put("max_id", maxId);
    	params.put("count", String.valueOf(NUMBER_TWEETS));

    	if(maxId.equals("-1"))
        	client.get(url, null, handler);
    	else
    		client.get(url, params, handler);
        		
    	
    }
    
    
    public void postNewTweet(AsyncHttpResponseHandler handler, String tweet) {
    	String url = getApiUrl("statuses/update.json");
    	client.post(url, new RequestParams("status", tweet), handler);
    	client.get(url,  null, handler);
    }
    
    
    public void verifyCredentials(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, handler);
    }

    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}