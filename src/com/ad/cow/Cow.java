package com.ad.cow;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Cow extends Activity {
	private ProgressBar mProgress;
	private SharedPreferences mySharedPreferences;
	private static String MY_PREFS = "MY_PREFS";
	
	private double perMinute = 0.083;
	private double percentByFood = 1.2;
	private int  percent;
	private long time;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        loadPreferences();
    }
    
    private void loadPreferences() {
    	int mode = Activity.MODE_PRIVATE;
    	long currentTime = new Date().getTime();
    	
    	mySharedPreferences = getSharedPreferences(MY_PREFS, mode);
    	percent = mySharedPreferences.getInt("percent", 0);
    	time    = mySharedPreferences.getLong("time", currentTime);
    	
    	long diff = currentTime - time;
    	double minutes = diff / 1000 / 60;
    	
    	double eatenFood = minutes * perMinute;
    	int cutPercent = (int) Math.round(eatenFood / percentByFood);
    	int newPercent = percent - cutPercent;
    	
    	percent = Math.max(0, newPercent);
    	
    	mProgress = (ProgressBar) findViewById(R.id.progressBar1);
        mProgress.setProgress(percent);
    }
    
    public void feed(View view) {
    	int newPercent = mProgress.getProgress()+10;
    	
    	if(newPercent <= 100) {
	    	SharedPreferences.Editor editor = mySharedPreferences.edit();
	    	editor.putInt("percent",newPercent);
	    	editor.putLong("time",new Date().getTime());
	    	editor.commit();
	    	
	    	mProgress.setProgress(newPercent);
    	}
    	
    	if(newPercent > 50){
    		Toast.makeText(this, "Ваша корова сыта. Приходите когда она проголодается!", 
    				Toast.LENGTH_LONG).show();
    	}
    }
}