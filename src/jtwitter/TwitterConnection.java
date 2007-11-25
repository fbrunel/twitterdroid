/*
 * Created on Apr 27, 2007
 */

package jtwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.xml.sax.SAXException;

/**
 * TwitterRequestSender sends http requests to the Twitter web service.
 * @author Lukasz Grzegorz Maciak
 *
 */
public class TwitterConnection {
	
	private static String PUBLIC_TIMELINE_URL = "http://twitter.com/statuses/public_timeline.xml";
	private static String FRIENDS_TIMELINE_URL = "http://twitter.com/statuses/friends_timeline.xml";
	private static String UPDATE_URL = "http://twitter.com/statuses/update.xml";

	private String username;
	private String password;
	
	public TwitterConnection(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Get the public timeline in XML format.
	 * 
	 * @return TwitterResponse containing the public timeline entries  
	 * @throws IOException
	 */
	public TwitterResponse getPublicTimeline()
		throws Exception {
		return new TwitterResponse().parse(getResponseBody(makeConnection(PUBLIC_TIMELINE_URL)));
	}
	
	/**
	 * Get the user's friends timeline (what you see on the homepage) with basic authentication.
	 * 
	 * @return TwitterResponse containing the friends timeline entries
	 * @throws IOException
	 */
	public TwitterResponse getFriendsTimeline() 
		throws ParseException, SAXException, ParserConfigurationException, IOException, TwitterConnectionException {
		return new TwitterResponse().parse(getResponseBody(makeAuthConnection(FRIENDS_TIMELINE_URL)));
	}
	
	public InputStream getFriendsTimelineStream() 
		throws IOException {
		return makeAuthConnection(FRIENDS_TIMELINE_URL).getInputStream();
	}
	
	/**
	 * Update your Twitter status. 
	 * 
	 * @param text String containing text you want to update your status with
	 * @return TwitterResponse containing response from Twitter server 
	 * @throws IOException
	 */
	public TwitterResponse updateStatus(String text) 
		throws ParseException, SAXException, ParserConfigurationException, 
			   IOException, TwitterConnectionException {
		
		if(text.length() > 140) {
			throw new IllegalArgumentException("Update text is longer than 140 characters");
		}
			
		String status = "status=" + URLEncoder.encode(text, "UTF-8");
		HttpURLConnection conn = makeAuthConnection(UPDATE_URL);
		
		sendPostRequest(conn, status);
		return new TwitterResponse().parse(getResponseBody(conn));
	}
	
	private HttpURLConnection makeConnection(String resource) 
		throws IOException {
		
		HttpURLConnection conn = (HttpURLConnection)(new URL(resource).openConnection());
        conn.setDoOutput(true);
		return conn;
	}
	
	private HttpURLConnection makeAuthConnection(String resource) 
		throws IOException {
		
		// Basic HTTP authentication requires the username:password pair to be base64 encoded
		String credentials = new String(new Base64().encode((username + ":" + password).getBytes()));
		
		HttpURLConnection conn = makeConnection(resource);
		conn.setRequestProperty ("Authorization", "Basic " + credentials);
		return conn;
	}
	
	private String getResponseBody(HttpURLConnection conn) 
		throws TwitterConnectionException {
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer output = new StringBuffer();
			while ((line = rd.readLine()) != null) { output.append(line); }
			rd.close(); 
			return output.toString();
		} catch (Exception e) {
			throw new TwitterConnectionException(conn, e);
		}
	}
		
	private void sendPostRequest(HttpURLConnection conn, String data) 
		throws TwitterConnectionException {
		try {
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();
		} catch (Exception e) {
			throw new TwitterConnectionException(conn, e);
		}
	}
}
