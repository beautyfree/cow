package com.ad.cow;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ad.cow.library.DatabaseHandler;
import com.ad.cow.library.GlobalVar;
import com.ad.cow.library.UserFunctions;

public class LoginActivity extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	
	private static String KEY_LOGIN_UID = "uid";
	private static String KEY_LOGIN_NAME = "name";
	private static String KEY_LOGIN_EMAIL = "email";
	private static String KEY_LOGIN_CREATED_AT = "created_at";
	
    private static final String KEY_DATA_ID = "id";
    private static final String KEY_DATA_LEVEL = "level";
    private static final String KEY_DATA_PERCENT = "percent";
    private static final String KEY_DATA_FEEDTIME = "feed_time";
    private static final String KEY_DATA_EXPTIME = "exp_time";
    private static final String KEY_DATA_EXP = "exp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Check login status in database
		UserFunctions userFunctions = new UserFunctions();
		if (userFunctions.isUserLoggedIn(this)) {
			// Launch Home Screen
			Intent intent = new Intent(this, HomeActivity.class);
			// Close all views before launching Home
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			// Close Login Screen
			finish();
		}
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.loginUser(email, password);

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						loginErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(
									json_user.getString(KEY_LOGIN_NAME),
									json_user.getString(KEY_LOGIN_EMAIL),
									json.getString(KEY_LOGIN_UID),
									json_user.getString(KEY_LOGIN_CREATED_AT)
							);
							
							json = userFunction.getUserData(getApplicationContext());
							try {
								String KEY_SUCCESS = "success";
								if (json.getString(KEY_SUCCESS) != null) {
									res = json.getString(KEY_SUCCESS);
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
										
										GlobalVar gv = GlobalVar.getInstance();
										gv.setLevel(json_data.getInt("level"));
										gv.setPercent((float)json_data.getDouble("percent"));
										gv.setTime(json_data.getLong("feed_time"));
										gv.setExpTime(json_data.getLong("exp_time"));
										gv.setExp((float)json_data.getDouble("exp"));
										
										Log.d("save",json_data.getString("level"));
									} else {
										// Error in logout
										//loginErrorMsg.setText("Incorrect username/password");
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
							// Launch Dashboard Screen
							Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
							// Close all views before launching Dashboard
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							// Close Login Screen
							finish();
						} else {
							// Error in login
							loginErrorMsg.setText("Incorrect username/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
}
