package com.fredbrunel.android.twitter;

import java.util.HashMap;

import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import jtwitter.TwitterResponse;

public class TwitterStatusAdapter implements ListAdapter {

	private ViewFactory factory;
	private TwitterResponse entries;
	private HashMap<Integer,View> views = new HashMap<Integer,View>();	
	
	public TwitterStatusAdapter(ViewFactory factory, TwitterResponse entries) 
		throws Exception {
		this.factory = factory;
		this.entries = entries;
		
		// Prefetch views
		for (int i = 0; i < entries.getNumberOfItems(); i++) {
			views.put(i, factory.makeUserStatusView(entries.getItemAt(i)));
		}
	}
	
	public boolean areAllItemsSelectable() {
		return true;
	}

	public boolean isSelectable(int position) {
		return true;
	}

	public int getCount() {
		return entries.getNumberOfItems();
	}

	public Object getItem(int position) {
		try {
			return entries.getItemAt(position);
		} catch (Exception e) {
			return null; // [FIXME]
		}
	}

	public long getItemId(int position) {
		try {
			return entries.getItemAt(position).getId();
		} catch (Exception e) {
			return position; // [FIXME]
		}
	}

	public int getNewSelectionForKey(int currentSelection, int keyCode, KeyEvent event) {
		return NO_SELECTION;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (views.containsKey(position)) { return views.get(position); }
			View status = factory.makeUserStatusView(entries.getItemAt(position));
			views.put(position, status);
			return status;
		} catch (Exception e) {
			return convertView; // [FIXME]
		}
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
}
