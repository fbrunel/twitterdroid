package jtwitter;

import java.io.IOException;
import java.net.HttpURLConnection;

public class TwitterConnectionException extends Exception {

	static final long serialVersionUID = -1L; // [FIXME]
	private HttpURLConnection conn;
	
	public TwitterConnectionException(HttpURLConnection conn, Throwable cause) {
		super(cause);
		this.conn = conn;
	}

	public int getResponseCode() { 
		try {
			return conn.getResponseCode();
		} catch (IOException e) {
			return -1;
		}
	}
	
	public String getResponseMessage() { 
		try {
			return conn.getResponseMessage();
		} catch (IOException e) {
			return null;
		}
	}
}
