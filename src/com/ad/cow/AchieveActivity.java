package com.ad.cow;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class AchieveActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achieve);
		
		Context context = getApplicationContext();
		MediaPlayer LevelUpPlayer = MediaPlayer.create(context, R.raw.levelup);
		LevelUpPlayer.start();
	}
	
	public void onClick(View view) {
		finish();
	}
}
