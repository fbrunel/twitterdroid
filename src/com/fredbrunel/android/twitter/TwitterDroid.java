package com.fredbrunel.android.twitter;

import jtwitter.TwitterConnection;
import jtwitter.TwitterResponse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class TwitterDroid extends Activity {	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        try {
        	TwitterResponse entries = new TwitterConnection().getFriendsTimeline("fbrunel", "wulfgar");
        	
        	setContentView(R.layout.main);
        	
        	ViewFactory factory = new ViewFactory(this);

            //EditText edit = (EditText)findViewById(R.id.message);
            //edit.setInputMethod(new SendInputMethod());
        
        	ListView list = (ListView)findViewById(R.id.list);
            list.setAdapter(new TwitterStatusAdapter(factory, entries));

        } catch (Exception e) {
           	Log.e("Crashed", e.getMessage(), e);
        }
    }
}

// [TODO] Unicode does not seem to be recognized (Japanese characters on the public timeline)

/*
  NotificationManager nm = NotificationManager.from(this);
  Notification n = new Notification();
  n.statusBarIcon = R.drawable.twitalert;
  n.statusBarTickerText = "hello android";
  n.statusBarBalloonText = "clicked!";
  //nm.notifyWithText(0, "hello android", NotificationManager.LENGTH_SHORT, n);
  nm.notify(0, n);
*/
