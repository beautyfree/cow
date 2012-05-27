package com.ad.cow;

import android.app.Application;

public class AbstractApplication extends Application {
	public void onCreate() {
		super.onCreate();
	
		GlobalVar.getInstance(getApplicationContext());
	}
}
