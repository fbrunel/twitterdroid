package com.fredbrunel.android.twitter;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCache {

	private static BitmapCache instance = null;
	private ConcurrentHashMap<URL, Bitmap> cache = new ConcurrentHashMap<URL, Bitmap>();
	
	private BitmapCache() {
	}
	
	public static BitmapCache getInstance() {
		if (instance == null) {
			instance = new BitmapCache();
		}
		return instance;
	}
	
	public boolean containsURL(URL url) {
		return cache.containsKey(url);
	}
	
	public Bitmap get(URL url) {
		return cache.get(url);
	}
		
	public Bitmap load(URL url) throws IOException {
		if (containsURL(url)) { return get(url); }
		Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
		cache.put(url, bitmap);
		return bitmap;
	}
}
