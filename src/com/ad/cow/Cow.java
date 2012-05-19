package com.ad.cow;

import com.ad.cow.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Cow extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    int satiety = 60;
    public void feed(View view){
    	if(satiety > 50){
    		Toast.makeText(this, "Ваша корова сыта. Приходите когда она проголодается!",
                    Toast.LENGTH_LONG).show();
    		return;
    	}
    }
}