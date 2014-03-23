package io.pivotal.pso.util;

import junit.framework.Assert;

import org.junit.Test;

public class JSONUtilTest {
	
	@Test
	public void testParsingJSONHostNameTest(){
		String jsonString = "{\"instance_id\": \"sfasdfasf\", \"application_uris\" : [\"localhost:8080\"]}";
		Assert.assertEquals("localhost:8080", JSONUtil.getHostNameFromVcapApplication(jsonString));
	}
}
