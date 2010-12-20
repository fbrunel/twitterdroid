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
	
	//
	
	public void commit() {
		editor.commit();
	}
	
	//
	
	public String getAccessKey() {
		return settings.getString("accessKey", "");
	}
	
	public void setAccessKey(String accessKey) {
		editor.putString("accessKey", accessKey);
	}
	
	public String getAccessSecret() {
		return settings.getString("accessSecret", "");
	}

	public void setAccessSecret(String accessSecret) {
		editor.putString("accessSecret", accessSecret);
	}
	
	public boolean authorized() {
		if ((getAccessKey() != "") && (getAccessSecret() != ""))
			return true;
		else
			return false;
	}
	
}
