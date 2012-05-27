package com.ad.cow;

import java.util.Date;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AbstractApplication extends Application {

	public void onCreate() {
		super.onCreate();
	
		GlobalVar.getInstance(getApplicationContext());
	}
}
