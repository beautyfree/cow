package com.ad.cow;

import com.ad.cow.library.GlobalVar;

import android.app.Application;

public class AbstractApplication extends Application {
	public void onCreate() {
		super.onCreate();
	
		GlobalVar.getInstance(getApplicationContext());
	}
}
