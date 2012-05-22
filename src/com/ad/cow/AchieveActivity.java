package com.ad.cow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AchieveActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achieve);
	}
	
	public void onClick(View view) {
		finish();
	}
}
