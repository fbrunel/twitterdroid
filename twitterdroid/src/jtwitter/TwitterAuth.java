package jtwitter;

import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.net.Uri;

public class TwitterAuth implements AuthConstants {
	private String accessKey = "";
	private String accessSecret = "";
	
	public TwitterAuth(Uri uri) {
		String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

		try {
			provider.retrieveAccessToken(consumer, verifier);
			accessKey = consumer.getToken();
			accessSecret = consumer.getTokenSecret();
			
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	
	public String getAccessSecret() {
		return accessSecret;
	}
}
