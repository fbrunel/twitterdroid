/*
 * Created on Apr 27, 2007
 */

package jtwitter;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TwitterEntry {
	
	// Twitter Entry Nodes (each corresponds to a XML node with the same name)
	public static final String CREATED_AT = "created_at";
	public static final String ID = "id";
	public static final String TEXT = "text";
	
	private Date createdAt;
	private long id;
	private String text;
	private TwitterUser user;
	
	//This is currently the date format used by twitter
	public static final String TWITTER_DATE_FORMAT = "EEE MMM dd kk:mm:ss Z yyyy";
	
	public TwitterEntry(Date createdAt, long id, String text, TwitterUser user) {
		super();
		this.createdAt = createdAt;
		this.id = id;
		this.text = text;
		this.user = user;
	}
	
	public TwitterEntry() {
		this.user = new TwitterUser();
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public TwitterUser getUser()
	{
		return user;
	}

	public void setUser(TwitterUser user)
	{
		this.user = user;
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TwitterEntry other = (TwitterEntry) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void addAttribute(String key, String value) 
		throws ParseException, MalformedURLException {

		if(key.equals(CREATED_AT))
			this.setCreatedAt(makeDate(value));
		else if(key.equals(ID))
			this.setId(Long.parseLong(value));
		else if (key.equals(TEXT))
			this.setText(value);
		else if (key.equals(TwitterUser.NAME))
			this.getUser().setName(value);
		else if (key.equals(TwitterUser.SCREEN_NAME))
			this.getUser().setScreenName(value);
		else if (key.equals(TwitterUser.LOCATION))
			this.getUser().setLocation(value);
		else if (key.equals(TwitterUser.DESCRIPTION))
			this.getUser().setDescription(value);
		else if (key.equals(TwitterUser.PROFILE_IMAGE_URL))
			this.getUser().setProfileImageURL(value);
		else if (key.equals(TwitterUser.URL))
			this.getUser().setUrl(value);
		else if (key.equals(TwitterUser.IS_PROTECTED))
			this.getUser().setProtected(Boolean.parseBoolean(value));
	}

	public String toString() {
		return "Created At: " + this.getCreatedAt() + "; " 
		+ "Status: " + this.getText() + "; "
		+ "User: " + this.getUser(); 
	}
	
	private Date makeDate(String date)
		throws ParseException {
		return new SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.US).parse(date);	
	}
}
