package com.ad.cow.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.ad.cow.HomeActivity;

import android.content.Context;
import android.content.Intent;
 
public class UserFunctions {
 
    private JSONParser jsonParser;
 
    private static String loginURL = "http://cow.devall.ru/api/";
    private static String registerURL = "http://cow.devall.ru/api/";
    private static String saveURL = "http://cow.devall.ru/api/";
    private static String getURL = "http://cow.devall.ru/api/";
    private static String ratingURL = "http://cow.devall.ru/api/";
 
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String save_tag = "save";
    private static String get_tag = "get";
    private static String rating_tag = "rating";
    
    // Коструктор
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
 
    /**
     * Функция выполняющая авторизацию пользователя
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
 
    /**
     * Функция выполняющая регистрация пользователя
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
 
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }
    
    /**
     * Функция сохраняющая данные пользователя
     * */
    public JSONObject saveUserData(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		String uid = db.getUserDetails().get("uid");
		HashMap<String, String> data = db.getUserData();
    	
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("tag", save_tag));
        params.add(new BasicNameValuePair("uid", uid));
        for(Entry<String, String> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(saveURL, params);
        // return json
        return json;
    }
    
    /**
     * Функция выполняющая авторизацию пользователя
     * @param email
     * @param password
     * */
    public JSONObject getUserData(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		String uid = db.getUserDetails().get("uid");
    	
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", get_tag));
        params.add(new BasicNameValuePair("uid", uid));
        
        JSONObject json = jsonParser.getJSONFromUrl(getURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
 
    /**
     * Функция возвращающая рейтинг
     * @param email
     * @param password
     * */
    public JSONObject getRating(Context context){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", rating_tag));
        
        JSONObject json = jsonParser.getJSONFromUrl(ratingURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
    
    /**
     * Функция получения статуса авторизации пользователя
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Функция выхода пользователя
     * Очищает таблицы
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
 
}