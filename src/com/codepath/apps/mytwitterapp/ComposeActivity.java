package com.codepath.apps.mytwitterapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void onPost(MenuItem m) {
		String tweetText = ((EditText) findViewById(R.id.etCompose)).getText().toString();
		String timeStamp = new SimpleDateFormat("EEE MMM d HH:m:s Z yyyy").format(Calendar.getInstance().getTime());
		
		
    	Intent data = getIntent();
    	data.putExtra("tweet_text", tweetText.toString());
    	data.putExtra("tweet_date", timeStamp);
    	setResult(RESULT_OK, data);
    	super.finish();
    	
	}
}
