package com.fredbrunel.android.twitter;

import java.util.HashMap;

import android.content.Context;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
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
	
	public StatusAdapter(Context context, TwitterResponse statuses) {
		this.statuses = statuses;
		
		for (int i = 0; i < statuses.getNumberOfItems(); i++)
			views.put(i, makeUserStatusView(context, statuses.getItemAt(i)));
	}
	
	public boolean isEmpty() { return views.isEmpty(); }
	public boolean isEnabled(int position) { return true; }
	public boolean areAllItemsEnabled() { return true; }
	public boolean hasStableIds() { return true; }
	public int getViewTypeCount() { return 1; }
	public int getItemViewType(int position) { return 1; }
	
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

	public void unregisterContentObserver(ContentObserver observer) {
	}
	
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}
	
	// Create the view for each user status
	// [FIXME] Should be put as an XML file (possible?)
	
    private View makeUserStatusView(Context context, TwitterEntry entry) {

    	ImageView iv = new ImageView(context);
    	Bitmap photo = BitmapCache.getInstance().get(entry.getUser().getProfileImageURL());

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
