package com.fredbrunel.android.twitter;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import jtwitter.AuthConstants;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ConfigActivity extends Activity implements AuthConstants {

	public static final int CONFIG_UPDATE_REQUEST = 0;
	
	public static void requestUpdate(Activity parent) {
    	Intent configure = new Intent(parent, ConfigActivity.class);
    	parent.startActivity(configure);
	}
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //Config config = Config.getConfig(this);
		//config.setAccessKey("");
		//config.setAccessSecret("");
        
    	try {    		
    		String authURL = provider.retrieveRequestToken(consumer, CALLBACK_URL);
    		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authURL)));
    	} catch (OAuthMessageSignerException e) {
    		e.printStackTrace();
    	} catch (OAuthNotAuthorizedException e) {
    		e.printStackTrace();
    	} catch (OAuthExpectationFailedException e) {
    		e.printStackTrace();
    	} catch (OAuthCommunicationException e) {
    		e.printStackTrace();
    	}
    	
    	finish();
    }
}
