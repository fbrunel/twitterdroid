package com.fredbrunel.android.twitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageView;

import jtwitter.AuthConstants;
import jtwitter.TwitterAuth;
import jtwitter.TwitterResponse;

public class StatusActivity extends Activity {	
	
	private static final int MENU_CONFIGURE_ID = Menu.FIRST;
	
	private ProgressDialog activeProgress;
	private TwitterService twitter = null;
	
    // Called when the activity is first created

	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
    	((ImageView)findViewById(R.id.splash_logo)).setImageResource(R.drawable.twitterdroid);
    }
    
	// Handles configuration updates and authorization (if required)
	
    @Override
    public void onResume() {
    	super.onResume();
    	
		Config config = Config.getConfig(this);
    	Uri uri = this.getIntent().getData();

    	if (uri != null && uri.toString().startsWith(AuthConstants.CALLBACK_URL)) {
    		TwitterAuth auth = new TwitterAuth(uri);
    		
			config.setAccessKey(auth.getAccessKey());
			config.setAccessSecret(auth.getAccessSecret());
			config.commit();
			
    	} else if (!config.authorized()) {
        	ConfigActivity.requestUpdate(this);
        	return;
    	}
    	
    	if (twitter == null) {			
			twitter = new TwitterService(config.getAccessKey(), config.getAccessSecret());
		}

    	showFetchingProgress();
		twitter.requestFriendsTimeline(handler);
    }
    
    // Handles menu
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(Menu.NONE, MENU_CONFIGURE_ID, Menu.NONE, R.string.status_configure_menu).
    		setShortcut('0', 'c');
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_CONFIGURE_ID:
        	ConfigActivity.requestUpdate(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
	
	// Handles messages in replies from requests to the Twitter Service
	
	private Handler handler = new Handler () {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == TwitterService.RESPONSE_OK) {
				switch(msg.arg2) {
					case TwitterService.REQUEST_FRIENDS_TIMELINE: updateStatusListView((TwitterResponse)msg.obj);
					case TwitterService.REQUEST_STATUS_UPDATE: clearEditMessageView();
				}	
			} else {
				String message = ((Exception) msg.obj).getMessage();
				new AlertDialog.Builder(StatusActivity.this).setTitle("Twitter Error").setMessage(message).setNegativeButton("Discard", null).setCancelable(false).show();
			}
			hideProgress();
		}
	};
	
	// Manages Views
	
	private void updateStatusListView(TwitterResponse statuses) {
		setContentView(R.layout.main);
	
		// [FIXME] Should be done once, only the status adapter should be refreshed.
		findViewById(R.id.status_message).requestFocus();
		findViewById(R.id.status_message).setOnClickListener(messageListener);
		findViewById(R.id.status_refresh).setOnClickListener(refreshListener);
		
		ListView list = (ListView)findViewById(R.id.status_list);
		list.setAdapter(new StatusAdapter(this, statuses));
	}
	
	private void clearEditMessageView() {
		((EditText)findViewById(R.id.status_message)).setText("");
	}
	
	private OnClickListener messageListener = new OnClickListener() {
		public void onClick(View v) {
			EditText edit = (EditText)v;
			String text = edit.getText().toString();
			showSendingProgress();
			twitter.requestUpdateStatus(text, handler);
		}
	};
	
	private OnClickListener refreshListener = new OnClickListener() {
		public void onClick(View v) {
			showFetchingProgress();
			twitter.requestFriendsTimeline(handler);
		}
	};
	
	// Progress notifications
	
	private void showFetchingProgress() { 
		activeProgress = ProgressDialog.show(this, null, "Please wait...", true, false);
	}
	
	private void showSendingProgress() {
		activeProgress = ProgressDialog.show(this, null, "Sending your message...", true, false);
	}
	
	private void hideProgress() {
		if (activeProgress != null) {
			activeProgress.dismiss();
			activeProgress = null;
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
