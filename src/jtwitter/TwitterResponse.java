/**
 * 
 */
package jtwitter;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

/**
 * @author Lukasz Grzegorz Maciak
 *
 */
public class TwitterResponse {
	
	private static final String TOP_LEVEL_NODE_NAME = "status";  // the top level <status> node to be read
	private static final String USER_NODE_NAME = "user";			// the <user> node which has sub-nodes
	private static final String TEXT_NODE_NAME = "#text"; 		// basic textual element
	
	DocumentBuilder builder;
	NodeList nodes;
	HashMap<Integer,TwitterEntry> entries = new HashMap<Integer,TwitterEntry>();

	/**
	 * Create JAXP based parser and read XML from an InputStream. 
	 * 
	 * @param xmlStream InputStream with XML data 
	 * 
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public TwitterResponse() 
		throws SAXException, IOException, ParserConfigurationException {
		
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	public TwitterResponse parse(InputStream xmlStream) 
		throws SAXException, IOException {
		
		Document d = builder.parse(xmlStream);
		nodes = d.getElementsByTagName(TOP_LEVEL_NODE_NAME);
		return this;
	}
	
	public TwitterResponse parse(String xmlString)
		throws SAXException, IOException {
		
		Document d = builder.parse(new InputSource(new StringReader(xmlString)));
		nodes = d.getElementsByTagName(TOP_LEVEL_NODE_NAME);
		return this;
	}
		
	/**
	 * Number of items read by this parser
	 * 
	 * @return Get the total number of &lt;status&gt; nodes returned
	 */
	public int getNumberOfItems() {
		return nodes.getLength();
	}
	
	/**
	 * Get the index'th &lt;status&gt node by walking the DOM
	 *  
	 * @param index the index of the node you want to get
	 * @return TwitterEntry representation of that node
	 * @throws ParseException 
	 */
	public TwitterEntry getItemAt(int index) 
		throws Exception {
		
		if (entries.containsKey(index)) {
			return entries.get(index);
		}
		
		TwitterEntry entry = new TwitterEntry();
		
		// nd is the list of child nodes of the <status> element with the specified index 
		NodeList nd = nodes.item(index).getChildNodes();
		
		// loop through all the children
		for(int i = 0; i < nd.getLength(); i++)
		{
			// for some reason JAXP treats white space as #text nodes, so let's skip them
			if(!nd.item(i).getNodeName().equals(TEXT_NODE_NAME))
			{
				// the <user> element has subnodes so we need additional processing
				if(nd.item(i).getNodeName().equals(USER_NODE_NAME))
				{
					// nd_usr will contain all the child nodes of <user>
					NodeList nd_usr = nd.item(i).getChildNodes();
					
					for(int j=0; j<nd_usr.getLength(); j++)
					{
						// once again we are skipping blank lines and whitespace
						if(!nd_usr.item(j).getNodeName().equals(TEXT_NODE_NAME))
						{
							// [TODO] figure out how to prevent User ID from overwriting post ID
							String value ="", name = nd_usr.item(j).getNodeName(); 	// get the name of the current node
							if(nd_usr.item(j).hasChildNodes())						// if not empty, also get the value
								value = nd_usr.item(j).getFirstChild().getNodeValue();
							
							entry.addAttribute(name, value);
						}
					}
				}
				else
				{
					String value ="", name = nd.item(i).getNodeName();
					if(nd.item(i).hasChildNodes())
						value = nd.item(i).getFirstChild().getNodeValue();
					
					entry.addAttribute(name, value);
					//System.out.println(name + " --> " + value);
				}
			}
		}
		
		entries.put(index, entry);
		return entry;
	}
}
