package com.ad.cow;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ListView;

import com.ad.cow.library.Rating;
import com.ad.cow.library.RatingAdapter;
import com.ad.cow.library.UserFunctions;

public class RatingActivity extends AbstractActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating);
		
		UserFunctions userFunctions = new UserFunctions();
		JSONObject json = userFunctions.getRating(this);
		
		ArrayList<Rating> items = new ArrayList<Rating>(); 
		try {    
			JSONArray jsonArray = (JSONArray) json.getJSONArray("rating");
			if (jsonArray != null) { 
				int len = jsonArray.length();
				for (int i=0;i<len;i++){ 
					JSONObject o = jsonArray.getJSONObject(i);
					items.add(new Rating(o.getString("name"),o.getInt("level"),(float)o.getDouble("exp")));
				} 
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		ListView list = (ListView) findViewById(R.id.list);
		RatingAdapter adapter = new RatingAdapter(this, R.layout.list_row, items);
        list.setAdapter(adapter);
	}
}