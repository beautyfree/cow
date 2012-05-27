package com.ad.cow;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ExperienceActivity extends AbstractActivity {
	/**
	 * Необходимые переменные
	 */	
	private GlobalVar gv;
	private final float expPerSecond = 0.002777778f;
	
	private float exp;
	private long time;
	private int level;
	private float newExp;
    
	/**
	 * Старт активности
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experience);
		
		Log.d("init", "onCreate");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d("init", "onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d("init", "onResume");
		
		loadPreferences();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d("init", "onStop");
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		Log.d("init", "onRestart");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("init", "onDestroy");
	}
	
	private void loadPreferences() {
		long currentTime = new Date().getTime();

		// Достаем сохраненные данные
		gv = GlobalVar.getInstance();
		time = gv.getExpTime();
		level = gv.getLevel();
		exp = gv.getExp();

		long diff = currentTime - time; 
		float seconds = diff / 1000;
		float addExp = seconds * expPerSecond;
		exp = exp + addExp; 

		TextView textView = (TextView) findViewById(R.id.textView1);
		textView.setText("У вас " + exp + " опыта");
		
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText("Вам добавилось " + addExp+ " опыта");
		
		
		handleLevelUp();
		
		double percentByExp = nettoXpNeededForLevel(level+1) / 100;
		double currentPercent = xpSinceLastLevelUp() / percentByExp;
		
		ProgressBar progressView = (ProgressBar) findViewById(R.id.progressBar1);
		progressView.setProgress((int)currentPercent);
		
		/*gv.setExpTime(new Date().getTime());
		gv.setLevel(level);
		gv.setExp(exp);
		gv.save();*/
	}

	
    /**
	* Check if the player has reached enough XP for a levelup
	*/
    private void handleLevelUp() {
        if (xpSinceLastLevelUp() >= nettoXpNeededForLevel(level+1)) {
            level++; 
        }
    }

    /**
	*
	* @param level to calculate summed up xp value for
	*
	* @return summed up xp value
	*/
    public double summedUpXpNeededForLevel(int level){
    	return 1.75 * Math.pow(level, 2) + 5.00 * level;
    }
    
    /**
	*
	* @param level to calculate netto xp value for
	*
	* @return netto xp value
	*/
    public double nettoXpNeededForLevel(int level){
        if (level == 0) return 0;
        return summedUpXpNeededForLevel(level) - summedUpXpNeededForLevel(level-1);
    }
    
    /**
	*
	* @return xp gained since last level up
	*/
    public double xpSinceLastLevelUp(){
        return exp - summedUpXpNeededForLevel(level);
    }  
    
	/**
	 * При завершении экшена сохраняем данные
	 */
	@Override
	protected void onPause() {
		gv.setExpTime(new Date().getTime());
		gv.setLevel(level);
		gv.setExp(exp);
		gv.save();
		
		Log.d("init", "onPause");
		
		super.onPause();
	}
}