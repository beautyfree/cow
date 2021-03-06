package com.ad.cow;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ad.cow.library.DatabaseHandler;
import com.ad.cow.library.GlobalVar;
import com.ad.cow.library.UserFunctions;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AbstractActivity extends SherlockActivity {

	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
				,WindowManager.LayoutParams.FLAG_FULLSCREEN
		);
			
		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "a14fbaa462a5c66");
	}
	
    @Override
    public void setContentView(int layoutResId) {
  		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) View.inflate(this, layoutResId, null);
		
		// Add the adView to it
		layout.addView(adView);
		

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest()); 
 
		
		super.setContentView(layout);
		adView.bringToFront();
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, R.id.ID_ACTION_HOME, Menu.NONE,
				R.string.action_label_home)
				.setIcon(R.drawable.ic_compose)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		menu.add(Menu.NONE, R.id.ID_ACTION_EXPERIENCE, Menu.NONE,
				R.string.action_label_experience)
				.setIcon(R.drawable.ic_search)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, R.id.ID_ACTION_RATING, Menu.NONE,
				R.string.action_label_status)
				.setIcon(R.drawable.ic_search)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(1, R.id.ID_EXIT_ACCOUNT, 1, R.string.exit);
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
        	case android.R.id.home:
        	case R.id.ID_ACTION_HOME:
				intent = new Intent(this, HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case R.id.ID_ACTION_EXPERIENCE:
				intent = new Intent(this, ExperienceActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case R.id.ID_ACTION_RATING:
				intent = new Intent(this, RatingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			case R.id.ID_EXIT_ACCOUNT:				
				UserFunctions userFunctions = new UserFunctions();
		        JSONObject json = userFunctions.saveUserData(this);
				
				String KEY_SUCCESS = "success";
		        // check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							// user successfully logout
							if(userFunctions.logoutUser(this)) {
								intent = new Intent(this, LoginActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
							}
							finish();
						} else {
							// Error in logout
							//loginErrorMsg.setText("Incorrect username/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;				
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		
    	// Сохраняем данные на сервере
    	new UserFunctions().saveUserData(getApplicationContext());
		
		super.onDestroy();
	}
}
