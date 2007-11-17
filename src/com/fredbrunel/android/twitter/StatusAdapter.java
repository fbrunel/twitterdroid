package com.fredbrunel.android.twitter;

import java.util.HashMap;

import android.content.Context;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import jtwitter.TwitterResponse;

public class StatusAdapter implements ListAdapter {

	private TwitterResponse statuses;
	private HashMap<Integer,View> views = new HashMap<Integer,View>();	
	
	public StatusAdapter(Context context, TwitterResponse statuses) 
		throws Exception {
		this.statuses = statuses;
		
		// Create all views
		for (int i = 0; i < statuses.getNumberOfItems(); i++) {
			views.put(i, StatusView.makeView(context, statuses.getItemAt(i)));
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
}
