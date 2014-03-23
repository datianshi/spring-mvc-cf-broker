package io.pivotal.pso.util;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.Assert;

public class JSONUtil {
	public final static ObjectMapper mapper = new ObjectMapper();

	public static void assertEquals(Object o1, Object o2) {
		try {
			String json1 = mapper.writeValueAsString(o1);
			String json2 = mapper.writeValueAsString(o2);
			Assert.isTrue(json1.equals(json2));
		} catch (IOException e) {

		}
	}
	
	public static String getHostNameFromVcapApplication(String vApplication){
		try {
			JsonNode rootNode = mapper.readValue(vApplication, JsonNode.class);
			JsonNode uris = rootNode.get("application_uris");
			return uris.path(0).getTextValue();
		} catch (Exception e) {
			throw new IllegalArgumentException("VCAP can not be parsed");
		}
	}
}
