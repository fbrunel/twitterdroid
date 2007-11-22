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
	private ProgressDialog progress = new ProgressDialog(this);
	private TwitterService twitter;
	
    /** Called when the activity is first created. */
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
	
	protected void onActivityResult(int requestCode, int resultCode, String data, Bundle extras) {
		if (requestCode == ConfigActivity.CONFIG_UPDATE_REQUEST && resultCode == RESULT_OK) {
			progress = ProgressDialog.show(this, null, "Please wait...", true, false);
			Config config = Config.getConfig(this);
			twitter = new TwitterService(config.getUsername(), config.getPassword());
			twitter.start(handler);
		}
	}
	
	// Handling Twitter Events
	
	private Handler handler = new Handler () {
		public void handleMessage(Message msg) {
			updateStatusList((TwitterResponse)msg.obj);
			progress.dismiss();
		}
	};
	
	// List View
	
	private void updateStatusList(TwitterResponse statuses) {
		setContentView(R.layout.main);
	
		EditText edit = (EditText)findViewById(R.id.status_message);
		edit.setOnClickListener(messageListener);

		ListView list = (ListView)findViewById(R.id.status_list);
		list.setAdapter(new StatusAdapter(this, statuses));
	}
	
	private OnClickListener messageListener = new OnClickListener() {
		public void onClick(View v) {
			EditText edit = (EditText)v;
			String text = edit.getText().toString();
			try {
				// [TODO] Remove spaces and check for length.
				//TwitterResponse status = twitter.updateStatus(text); // Thread it!
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
