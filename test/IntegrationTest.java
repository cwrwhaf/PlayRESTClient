import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.Media;

import org.junit.Test;

import play.libs.WS.Response;

public class IntegrationTest {

	@Test
	public void callAuthenticateRestService() {
		Response result = Media.callAuthenticateService().get();

		assertNotNull("Expecting a response from service", result);
		assertEquals("Expecting Ok response", 200, result.getStatus());
	}

	@Test
	public void getAuthentcationTokenTest() {
		String authenticationToken = Media.getAuthenticationToken();

		// could do with a better way of determining success in recieving token
		assertNotNull("Expecting a token", authenticationToken);
	}

	@Test
	public void getMediaListTest() {
		List<String> media = Media.getMediaList();
		
		assertNotNull("Expecting a media list", media);
		assertEquals("Endeavour Launches on Mission STS-123", media.get(0));
	}

	@Test
	public void callMediaRestService() {
		Response result = Media.callMediaService().get();

		assertNotNull("Expecting a response from service", result);
		assertEquals("Expecting Ok response", 200, result.getStatus());

	}
}
