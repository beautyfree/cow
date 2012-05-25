package com.ad.cow;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ExperienceActivity extends AbstractActivity {

	private final String MY_PREFS = "MY_PREFS";
	private SharedPreferences mySharedPreferences;
	private long time;
	private long exp;

	  /**
	 * Старт активности
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experience);

		loadPreferences();
	}

	private void loadPreferences() {
		int mode = Activity.MODE_PRIVATE;
		long currentTime = new Date().getTime();

		// Достаем сохраненные данные
		mySharedPreferences = getPreferences(mode);
		exp  = mySharedPreferences.getLong("exp", 10);
		time = mySharedPreferences.getLong("time", currentTime);
		
		long diff = currentTime - time; 
		float hours = diff / 1000 / 60 / 60;
		long expPerHour = (long) (hours * 10);

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("У вас " + exp + " опыта");

		int percent = 60; 
		ProgressBar progressView = (ProgressBar) findViewById(R.id.progressBar1);
		progressView.setProgress((int)percent);
	}

	/**
	 * При завершении экшена сохраняем данные
	 */
	@Override
	protected void onStop() {
		super.onStop();

		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putLong("exp", exp);
		editor.putLong("time", new Date().getTime());
		editor.commit();
	}
}