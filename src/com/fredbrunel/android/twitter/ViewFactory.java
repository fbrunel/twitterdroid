package com.fredbrunel.android.twitter;

import java.io.IOException;

import jtwitter.TwitterEntry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class ViewFactory {
	
	private Context context;
	
	public ViewFactory(Context context) {
		this.context = context;
	}
	
    public View makeUserStatusView(TwitterEntry entry) 
    	throws IOException {

    	ImageView iv = new ImageView(context);
    	Bitmap photo = BitmapFactory.decodeStream(entry.getUser().getProfileImageURL().openStream());
    	
    	iv.setImageBitmap(photo);
    	iv.setScaleType(ScaleType.CENTER);
    	iv.setPadding(0, 4, 4, 0);
    	iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	
    	TextView tv = new TextView(context);
    	tv.setText(entry.getUser().getName() + ": " + entry.getText());
    	tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	
    	LinearLayout layout = new LinearLayout(context);
    	layout.setOrientation(LinearLayout.HORIZONTAL);
    	layout.setPadding(4, 2, 4, 2);
    	layout.addView(iv);
    	layout.addView(tv);
    	
    	return layout;
    }
}
