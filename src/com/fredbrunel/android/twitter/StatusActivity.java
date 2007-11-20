package com.fredbrunel.android.twitter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import jtwitter.TwitterConnection;
import jtwitter.TwitterResponse;

public class StatusActivity extends Activity {	
	
	private static final int MENU_CONFIGURE_ID = Menu.FIRST;
	private TwitterConnection twitter = new TwitterConnection("fbrunel", "wulfgar");
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        try {
        	TwitterResponse statuses = twitter.getFriendsTimeline();
        	
        	setContentView(R.layout.main);
        	//setContentView(R.layout.sample);
        	
            EditText edit = (EditText)findViewById(R.id.status_message);
            edit.setOnClickListener(messageListener);
        
        	ListView list = (ListView)findViewById(R.id.status_list);
            list.setAdapter(new StatusAdapter(this, statuses));

        } catch (Exception e) {
        	// [FIXME] Handle the exception elsewhere
           	Log.e("Crashed", e.getMessage(), e);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_CONFIGURE_ID, R.string.status_configure_menu).
    		setShortcut(KeyEvent.KEYCODE_0, 0, KeyEvent.KEYCODE_C);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(Menu.Item item) {
        switch (item.getId()) {
        case MENU_CONFIGURE_ID:
        	ConfigActivity.start(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }    
    
	OnClickListener messageListener = new OnClickListener() {
		public void onClick(View v) {
			EditText edit = (EditText)v;
			String text = edit.getText().toString();
			try {
				// [TODO] Remove spaces and check for length.
				TwitterResponse status = twitter.updateStatus(text);
				edit.setText("");
			} catch (Exception e) {
				// [FIXME] Handle error;
			}
		}
	};
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
