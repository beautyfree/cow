package com.ad.cow;

import com.ad.cow.library.UserFunctions;

import android.os.Bundle;

public class RatingActivity extends AbstractActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating);
		
		UserFunctions userFunction = new UserFunctions();
	}
}