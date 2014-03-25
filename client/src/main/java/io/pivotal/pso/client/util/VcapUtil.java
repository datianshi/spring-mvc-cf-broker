package io.pivotal.pso.client.util;

import io.pivotal.pso.client.domain.Credential;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;


public class VcapUtil {
	public final static String VCAP_APPLICATION;
	public final static String VCAP_SERVICES;
	public final static Credential CREDENTIAL;

	static {
		VCAP_APPLICATION = System.getenv("VCAP_APPLICATION") != null ? System
				.getenv("VCAP_APPLICATION") : System
				.getProperty("VCAP_APPLICATION");

		VCAP_SERVICES = System.getenv("VCAP_SERVICES") != null ? System
				.getenv("VCAP_SERVICES") : "{\"dummy_service_name\":[{\"name\":\"test-service\",\"label\":\"dummy_service_name\",\"tags\":[],\"plan\":\"plan1\",\"credentials\":{\"url\":\"shaozhen.10.244.0.34.xip.io/restmap/5d2a0e66-a8f4-4740-9840-e8e2a5d33ae3/0d510b18-123d-4c01-9846-2fa7d0ff3b36\",\"username\":\"f8646cdb-29d7-4fe9-a6a2-510a691c6d4a\",\"password\":\"9c89b2be-7dcc-4123-9a86-002cd95d00d2\"}}]}";
				
		System.out.println("My vcap Services: " + VCAP_SERVICES);
		CREDENTIAL = getCredential(VCAP_SERVICES);
	}
	
	
	public static Credential getCredential(String json){
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readValue(json, JsonNode.class);
			return mapper.readValue(rootNode.get("dummy_service_name").get(0).get("credentials"), Credential.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("vCapService can not be parsed", e);
		}
	}
}
