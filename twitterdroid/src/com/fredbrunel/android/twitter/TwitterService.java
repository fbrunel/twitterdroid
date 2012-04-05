package com.fredbrunel.android.twitter;

import android.os.Handler;
import android.os.Message;
import jtwitter.TwitterConnection;
import jtwitter.TwitterConnectionException;
import jtwitter.TwitterResponse;

public class TwitterService {
	
	public static final int RESPONSE_OK = 0;
	public static final int RESPONSE_KO = 1;
	public static final int RESPONSE_CONN_KO = 2;
	
	public static final int REQUEST_FRIENDS_TIMELINE = 10;
	public static final int REQUEST_STATUS_UPDATE = 11;
	
	private TwitterConnection twitter;
	
	public TwitterService(String accessKey, String accessSecret) {
		this.twitter = new TwitterConnection(accessKey, accessSecret);
	}
	
	public void requestFriendsTimeline(Handler response) {
		new Thread(new DoGetFriendsTimeline(response)).start();
	}
	
	public void requestUpdateStatus(String text, Handler response) {
		new Thread(new DoStatusUpdate(text, response)).start();
	}
	
	// Twitter operations
	
	private class DoGetFriendsTimeline implements Runnable {
		private Handler handler;
		
		public DoGetFriendsTimeline(Handler handler) {
			this.handler = handler;
		}
		
		public void run() {
			Message msg = new Message();
			msg.arg2 = REQUEST_FRIENDS_TIMELINE;
			try {
				TwitterResponse statuses = twitter.getFriendsTimeline();
				
				// Load and cache all profile bitmaps
				BitmapCache cache = BitmapCache.getInstance();
				for (int i = 0; i < statuses.getNumberOfItems(); i++)
					cache.load(statuses.getItemAt(i).getUser().getProfileImageURL());
				
				msg.arg1 = RESPONSE_OK;
				msg.obj = statuses;
			} catch (TwitterConnectionException e) {
				msg.arg1 = RESPONSE_CONN_KO;
				msg.obj = e;
			} catch (Exception e) {
				msg.arg1 = RESPONSE_KO;
				msg.obj = e;
			}
			handler.sendMessage(msg);
		}
	}

	private class DoStatusUpdate implements Runnable {
		private Handler handler;
		private String text;
		
		public DoStatusUpdate(String text, Handler response) {
			this.text = text;
			this.handler = response;
		}
		
		public void run() {
			Message msg = new Message();
			msg.arg2 = REQUEST_STATUS_UPDATE;
			try {
				TwitterResponse status = twitter.updateStatus(text);
				msg.arg1 = RESPONSE_OK;
				msg.obj = status;
			} catch (TwitterConnectionException e) {
				msg.arg1 = RESPONSE_CONN_KO;
				msg.obj = e;
			} catch (Exception e) {
				msg.arg1 = RESPONSE_KO;
				msg.obj = e;
			}
			handler.sendMessage(msg);
		}
	}	
	
}
