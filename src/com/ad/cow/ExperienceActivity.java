package com.ad.cow;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ExperienceActivity extends AbstractActivity {

	private long time;
	private final String MY_PREFS = "MY_PREFS";
	private long exp = 100;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experience);
		
		loadPreferences();
		
		}
	public void loadPreferences(){
		int mode = Activity.MODE_PRIVATE;
		
		long currentTime = new Date().getTime();
		
		SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, mode);
		time = mySharedPreferences.getLong("time", currentTime);
		
		long diff = currentTime - time;
		float hours = diff / 360;
		long expPerHour = (long) (hours * 10);
		long exp = mySharedPreferences.getLong("exp", 10);

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("У вас " + exp + " опыта");
		
		int percent = 60;
		ProgressBar progressView = (ProgressBar) findViewById(R.id.progressBar1);
		progressView.setProgress((int)percent);
		

		SharedPreferences.Editor editor = mySharedPreferences.edit();
	    editor.putLong("exp", exp);
	    editor.putLong("time", new Date().getTime());
	    editor.commit();
	}
}