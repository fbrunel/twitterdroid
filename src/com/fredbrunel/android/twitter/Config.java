package com.fredbrunel.android.twitter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config {

	public static final String PREFS_NAME = "TwitterDroidPrefs";
	private SharedPreferences settings;
	private Editor editor;
	
	public static Config getConfig(Context context) {
		return new Config(context);
	}
	
	private Config(Context context) {
		settings = context.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
	}
	
	public void commit() {
		editor.commit();
	}
	
	//
	
	public String getUsername() {
		return settings.getString("username", "");
	}
	
	public void setUsername(String username) {
		editor.putString("username", username);
	}
	
	public String getPassword() {
		return settings.getString("password", "");
	}

	public void setPassword(String password) {
		editor.putString("password", password);
	}	
	
}
