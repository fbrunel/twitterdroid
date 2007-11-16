/*
 * Created on Apr 27, 2007
 */

package jtwitter;

import java.net.URL;

/**
 * TwitterUser Class holds information about a given Twitter user
 * 
 * @author Lukasz Grzegorz Maciak
 *
 */
public class TwitterUser {
	
	// Twitter User Nodes (each corresponds to a XML node with the same name)
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String SCREEN_NAME = "screen_name";
	public static final String LOCATION = "location";
	public static final String DESCRIPTION = "description";
	public static final String PROFILE_IMAGE_URL = "profile_image_url";
	public static final String URL = "url";
	public static final String IS_PROTECTED = "isProtected";
	
	private int id;
	private String name;
	private String screenName;
	private String location;
	private String description;
	private URL profileImageURL;
	private URL url;
	private boolean isProtected;
	
	public TwitterUser(int id, String name, String screenName, String location, String description, String profileImageURL, String url, boolean isProtected) 
		throws Exception {
		
		this.id = id;
		this.name = name;
		this.screenName = screenName;
		this.location = location;
		this.description = description;
		this.profileImageURL = new URL(profileImageURL);
		this.url = new URL(url);
		this.isProtected = isProtected;
	}
	
	public TwitterUser() {
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isProtected() {
		return isProtected;
	}
	
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public URL getProfileImageURL() {
		return profileImageURL;
	}

	public void setProfileImageURL(String profileImageURL)
		throws Exception {
		this.profileImageURL = new URL(profileImageURL);
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(String url) 
		throws Exception {
		this.url = new URL(url);
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TwitterUser other = (TwitterUser) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String toString() {
		return "[ " + getName() + " (" + getScreenName() + ") ]";
	}
}
