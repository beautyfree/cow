package com.ad.cow.library;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
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
	private static DatabaseHandler db;
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
					
					setLevel(json_data.getInt("level"));
					setPercent((float)json_data.getDouble("percent"));
					setTime(json_data.getLong("feed_time"));
					setExpTime(json_data.getLong("exp_time"));
					setExp((float)json_data.getDouble("exp"));
					save();	
					
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
			setLevel(Integer.parseInt(data.get("level")));
			setPercent(Float.parseFloat(data.get("percent")));
			setTime(Long.parseLong(data.get("feed_time")));
			setExpTime(Long.parseLong(data.get("exp_time")));
			setExp(Float.parseFloat(data.get("exp")));
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
