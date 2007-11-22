package com.fredbrunel.android.twitter;

import android.os.Handler;
import android.os.Message;
import jtwitter.TwitterConnection;
import jtwitter.TwitterResponse;

public class TwitterService implements Runnable {
	
	private TwitterConnection twitter;
	private Handler handler;
	
	public TwitterService(String username, String password) {
		twitter = new TwitterConnection(username, password);
	}
	
	public void start(Handler handler) {
		this.handler = handler;
		new Thread(this).start();
	}
	
	public void run() {
		try {
			TwitterResponse statuses = twitter.getFriendsTimeline();
			
			// Load and cache all profile bitmaps
			BitmapCache cache = BitmapCache.getInstance();
			for (int i = 0; i < statuses.getNumberOfItems(); i++)
				cache.load(statuses.getItemAt(i).getUser().getProfileImageURL());
			
			// Notifies the completion
			Message msg = new Message();
			msg.obj = statuses;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// [FIXME] Handle errors
		}
	}
}
