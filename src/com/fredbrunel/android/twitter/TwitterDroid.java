package com.fredbrunel.android.twitter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import jtwitter.TwitterConnection;
import jtwitter.TwitterResponse;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import android.app.NotificationManager;
import android.app.Notification;

public class TwitterDroid extends Activity {	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        try {
        	// TwitterResponse entries = TwitterConnection.getPublicTimeline();
        	TwitterResponse entries = new TwitterConnection().getFriendsTimeline("fbrunel", "wulfgar");
        	
        	RelativeLayout etl = new RelativeLayout(this);
        	EditText et = new EditText(this);
        	et.setLines(1);
        	et.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        	etl.addView(et);

        	//View view = new ViewFactory(this).makeStatusView(entries.getItemAt(0));
        	View view = new ViewFactory(this).makeTimelineView(entries);
        	
        	setContentView(view);
        	
        	/*
        	NotificationManager nm = NotificationManager.from(this);
        	Notification n = new Notification();
        	n.statusBarIcon = R.drawable.twitalert;
        	n.statusBarTickerText = "hello android";
        	n.statusBarBalloonText = "clicked!";
        	//nm.notifyWithText(0, "hello android", NotificationManager.LENGTH_SHORT, n);
        	nm.notify(0, n);
        	*/
        } catch (Exception e) {
        	Log.e("app", "exception", e);
        }
    }
}

// [TODO] Unicode does not seem to be recognized (Japanese characters on the public timeline)