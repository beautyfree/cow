package com.ad.cow.library;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.ad.cow.LoginActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GlobalVar extends Application {
	private int _level = 0;
	private float _percent = 0.0f;
	private long _time = new Date().getTime();
	private long _expTime = new Date().getTime();
	private float _exp = 0.0f;

	public int getLevel() {
		return _level;
	}

	public float getPercent() {
		return _percent;
	}

	public long getTime() {
		return _time;
	}

	public long getExpTime() {
		return _expTime;
	}

	public float getExp() {
		return _exp;
	}

	public void setLevel(int level) {
		this._level = level;
	}

	public void setPercent(float percent) {
		this._percent = percent;
	}

	public void setTime(long time) {
		this._time = time;
	}

	public void setExpTime(long expTime) {
		this._expTime = expTime;
	}

	public void setExp(float exp) {
		this._exp = exp;
	}

	/**
	 **********************************************
	 */
	private DatabaseHandler db;
	private static GlobalVar instance;

	static {
		instance = new GlobalVar();
	}

	private GlobalVar() {
	}

	public static GlobalVar getInstance(Context context) {
		GlobalVar gv = GlobalVar.instance;
		gv.LoadPreferences(context);
		return gv;
	}

	private void LoadPreferences(Context context) {
		db = new DatabaseHandler(context);
		UserFunctions userFunction = new UserFunctions();
		
		JSONObject json = userFunction.getUserData(context);
		try {
			String KEY_SUCCESS = "success";
			if (json.getString(KEY_SUCCESS) != null) {
				String res = json.getString(KEY_SUCCESS);
				if (Integer.parseInt(res) == 1) {
					// user successfully logout
					JSONObject json_data = json.getJSONObject("data");
					
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("level", json_data.getString("level"));
					data.put("percent", json_data.getString("percent"));
					data.put("feed_time", json_data.getString("feed_time"));
					data.put("exp_time", json_data.getString("exp_time"));
					data.put("exp", json_data.getString("exp"));
					db.saveUserData(data);	
					
				} else {
					// Error in logout
					//loginErrorMsg.setText("Incorrect username/password");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> data = db.getUserData();
		if(!data.isEmpty()) {
			this._level = Integer.parseInt(data.get("level"));
			this._percent = Float.parseFloat(data.get("percent"));
			this._time = Long.parseLong(data.get("feed_time"));
			this._expTime = Long.parseLong(data.get("exp_time"));
			this._exp = Float.parseFloat(data.get("exp"));
		}
	}

	public static GlobalVar getInstance() {
		return GlobalVar.instance;
	}

	public void save() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("level", Integer.toString(_level));
		data.put("percent", Float.toString(_percent));
		data.put("feed_time", Long.toString(_time));
		data.put("exp_time", Long.toString(_expTime));
		data.put("exp", Float.toString(_exp));
		db.saveUserData(data);
	}
}
