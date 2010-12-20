package jtwitter;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

public interface AuthConstants {
	public static final String CONSUMER_KEY = "iUwjRSp5vzqSFMWfbNysA";
	public static final String CONSUMER_SECRET = "a52EB1b1H18pblgKgKWIRhDJli1zE2vN60hNsDIc";

	public static final String REQUEST_URL = "http://twitter.com/oauth/request_token";
	public static final String ACCESS_TOKEN_URL = "http://twitter.com/oauth/access_token";
	public static final String AUTH_URL = "http://twitter.com/oauth/authorize";
	
	public static final String CALLBACK_URL = "OauthTwitter://twitter";
	public static final String PREFERENCE_FILE = "twitter_oauth.prefs";

	public static DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(
			CONSUMER_KEY, CONSUMER_SECRET);
	public static DefaultOAuthProvider provider = new DefaultOAuthProvider(
			REQUEST_URL, ACCESS_TOKEN_URL, AUTH_URL);
}
