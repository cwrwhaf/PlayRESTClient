package models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.XPath;

public class Media {

	private static final String XPATH_MEDIA_TITLE_TEXT_QUERY = "//media/title/text()";
	private static final String XPATH_TOKEN_QUERY = "//token";
	private static final String TOKEN = "token";
	private static final String KEY = "key";
	private static final String APPLICATION_ALIAS = "applicationalias";
	// refactor to a config file
	private static final String APPLICATION_ALIAS_VALUE = "flash_test_app";
	private static final String AUTHENTICATE_URL = "http://api.movideo.com/rest/session";
	private static final String KEY_VALUE = "dev";
	private static final String MEDIA_URL = "http://api.movideo.com/rest/media";
	
	
	public static Promise<Response> callAuthenticateService() {
		Promise<WS.Response> response = WS.url(AUTHENTICATE_URL)
				.setQueryParameter(APPLICATION_ALIAS, APPLICATION_ALIAS_VALUE)
				.setQueryParameter(KEY, KEY_VALUE).get();
	
		return response;
	
	}
	
	public static Promise<Response> callMediaService() {
		Promise<WS.Response> response = WS.url(MEDIA_URL)
				.setQueryParameter(TOKEN, getAuthenticationToken()).get();
	
		return response;
	}
	
	public static String getAuthenticationToken() {
		Response response = Media.callAuthenticateService().get();
	
		Document doc;
		String authenticationToken = "";
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(response.asByteArray()));
	
			authenticationToken = XPath.selectText(XPATH_TOKEN_QUERY, doc);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return authenticationToken;
	}
	public static List<String> getMediaList() {
	
		Response response = Media.callMediaService().get();
	
		Document doc;
		List<String> mediaList = new ArrayList<String>();
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(response.asByteArray()));
	
			NodeList nodes = XPath.selectNodes(XPATH_MEDIA_TITLE_TEXT_QUERY, doc);
			for (int x = 0; x < nodes.getLength(); x++) {
				mediaList.add(nodes.item(x).getNodeValue());
			}
	
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return mediaList;
	
	}

}
