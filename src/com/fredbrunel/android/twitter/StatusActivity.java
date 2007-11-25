package com.fredbrunel.android.twitter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageView;

import jtwitter.TwitterResponse;

public class StatusActivity extends Activity {	
	
	private static final int MENU_CONFIGURE_ID = Menu.FIRST;
	private ProgressDialog activeProgress = new ProgressDialog(this);
	private TwitterService twitter;
	
    // Called when the activity is first created

	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
    	((ImageView)findViewById(R.id.splash_logo)).setImageResource(R.drawable.logo);
    }

    @Override
    public void onStart() {
    	super.onStart();
    	//ConfigActivity.requestUpdate(this);
    }
    
    // Handles menu
    
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
        	ConfigActivity.requestUpdate(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
	
    // Handles configuration changes
    
	protected void onActivityResult(int requestCode, int resultCode, String data, Bundle extras) {
		if (requestCode == ConfigActivity.CONFIG_UPDATE_REQUEST && resultCode == RESULT_OK) {
			showFetchingProgress();
			Config config = Config.getConfig(this);
			twitter = new TwitterService(config.getUsername(), config.getPassword());
			twitter.requestFriendsTimeline(handler);
		}
	}
	
	// Handles messages in replies from requests to the Twitter Service
	
	private Handler handler = new Handler () {
		public void handleMessage(Message msg) {
			if (msg.arg1 == TwitterService.RESPONSE_OK) {
				switch(msg.arg2) {
					case TwitterService.REQUEST_FRIENDS_TIMELINE: updateStatusListView((TwitterResponse)msg.obj);
					case TwitterService.REQUEST_STATUS_UPDATE: clearEditMessageView();
				}	
			} else {
				showAlert("Twitter Error", ((Exception)msg.obj).getMessage(), "Discard", false);
			}
			hideProgress();
		}
	};
	
	// Manages Views
	
	private void updateStatusListView(TwitterResponse statuses) {
		setContentView(R.layout.main);
	
		EditText edit = (EditText)findViewById(R.id.status_message);
		edit.setOnClickListener(messageListener);

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
	
	// Progress notifications
	
	private void showFetchingProgress() { 
		activeProgress = ProgressDialog.show(this, null, "Please wait...", true, false);
	}
	
	private void showSendingProgress() {
		activeProgress = ProgressDialog.show(this, null, "Sending your message...", true, false);
	}
	
	private void hideProgress() {
		activeProgress.dismiss();
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
