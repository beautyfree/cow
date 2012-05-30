package com.ad.cow;

import org.json.JSONObject;

import com.ad.cow.library.GlobalVar;
import com.ad.cow.library.UserFunctions;

import android.app.Application;

public class AbstractApplication extends Application {
	public void onCreate() {
		super.onCreate();
	
		// Вытастикаем данные с сервера и создаем синглтон для работы с данными
		GlobalVar.getInstance(getApplicationContext());
	}
}
