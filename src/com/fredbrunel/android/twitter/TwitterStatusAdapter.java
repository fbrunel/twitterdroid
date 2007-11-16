package com.fredbrunel.android.twitter;

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
	
	public TwitterStatusAdapter(ViewFactory factory, TwitterResponse entries) 
		throws Exception {
		this.factory = factory;
		this.entries = entries;
	}
	
	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		return false;
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
			return factory.makeUserStatusView(entries.getItemAt(position));
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
