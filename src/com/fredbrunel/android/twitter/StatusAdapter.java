package com.fredbrunel.android.twitter;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

import jtwitter.TwitterEntry;
import jtwitter.TwitterResponse;

public class StatusAdapter implements ListAdapter {

	private TwitterResponse statuses;
	private HashMap<Integer,View> views = new HashMap<Integer,View>();	
	
	public StatusAdapter(Context context, TwitterResponse statuses) 
		throws Exception {
		this.statuses = statuses;
		
		// Create all views
		for (int i = 0; i < statuses.getNumberOfItems(); i++) {
			views.put(i, makeView(context, statuses.getItemAt(i)));
		}
	}
	
	public boolean areAllItemsSelectable() {
		return true;
	}

	public boolean isSelectable(int position) {
		return true;
	}

	public int getCount() {
		return statuses.getNumberOfItems();
	}

	public Object getItem(int position) {
		return statuses.getItemAt(position);
	}

	public long getItemId(int position) {
		return statuses.getItemAt(position).getId();
	}

	public int getNewSelectionForKey(int currentSelection, int keyCode, KeyEvent event) {
		return NO_SELECTION;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return views.get(position);
	}

	public void registerContentObserver(ContentObserver observer) {
	}

	public void registerDataSetObserver(DataSetObserver observer) {
	}

	public boolean stableIds() {
		return true;
	}

	public void unregisterContentObserver(ContentObserver observer) {
	}
	
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}
	
	// Create the view for each user status
	// [FIXME] Should be put as an XML file
	
    private View makeView(Context context, TwitterEntry entry) 
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
