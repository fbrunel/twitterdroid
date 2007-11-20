package com.fredbrunel.android.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends Activity {

	public static final String PREFS = "TwitterDroidPrefs";
	
	private Config config;
	private EditText eUsername;
	private EditText ePassword;
	
	public static void start(Context context) {
    	Intent configure = new Intent();
    	configure.setClassName(context, "com.fredbrunel.android.twitter.ConfigActivity");
    	context.startActivity(configure);
	}
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        setContentView(R.layout.configure);     
        
        eUsername = ((EditText)findViewById(R.id.config_username));
        ePassword = ((EditText)findViewById(R.id.config_password));
        ((Button)findViewById(R.id.config_apply)).setOnClickListener(applyListener);
        
        // Setup the content of the view
        config = Config.getConfig(this);
        eUsername.setText(config.getUsername());
        ePassword.setText(config.getPassword());
    }
    
    /**
     * Callback for when the user presses the apply button.
     */
    OnClickListener applyListener = new OnClickListener() {
        public void onClick(View v) {
        	config.setUsername(eUsername.getText().toString());
        	config.setPassword(ePassword.getText().toString());
        	config.commit();
            finish();
        }
    };
}
