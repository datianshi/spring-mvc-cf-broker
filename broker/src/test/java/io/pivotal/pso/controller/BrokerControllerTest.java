package io.pivotal.pso.controller;

import io.pivotal.pso.RestServer;
import io.pivotal.pso.domain.BindingRequest;
import io.pivotal.pso.domain.BindingResponse;
import io.pivotal.pso.domain.Catalog;
import io.pivotal.pso.domain.ProvisionRequest;
import io.pivotal.pso.dummy.DummyProvisionService;
import io.pivotal.pso.dummy.KeyValue;
import io.pivotal.pso.util.JSONUtil;

import java.util.HashMap;

import junit.framework.Assert;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class BrokerControllerTest {
	
	RestTemplate restTemplate;
	
	RestTemplate restAuthTemplate;

	@BeforeClass
	public static void before() throws Exception {
		RestServer.start();
	}
	
	@AfterClass
	public static void after() throws Exception{
		RestServer.stop();
	}
	
	@Before
	public void setUp(){
		restTemplate = new RestTemplate();
		

		UsernamePasswordCredentials credentials =
		new UsernamePasswordCredentials("shaozhen","inmemory");
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope("localhost", 8080, AuthScope.ANY_REALM),
				  credentials);
		
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		
		HttpComponentsClientHttpRequestFactory commons = new HttpComponentsClientHttpRequestFactory(client);
//		CommonsClientHttpRequestFactory commons =
//		     new CommonsClientHttpRequestFactory(client);

		restAuthTemplate  = new RestTemplate(commons);
	}

	@Test
	public void testCatalog() throws Exception {
		Catalog response = restAuthTemplate.getForEntity("http://localhost:8080/v2/catalog", Catalog.class).getBody();
		JSONUtil.assertEquals(DummyProvisionService.STATIC_CATALOG, response);
	}
	
	@Test
	public void testProvision() throws Exception {
		ProvisionRequest provisionRequest = new ProvisionRequest();
		provisionRequest.setService_id("dummy_service_id");
		provisionRequest.setPlan_id("plan1");
		restAuthTemplate.put("http://localhost:8080/v2/service_instances/service1", provisionRequest);
	}
	
	@Test
	public void testBinding(){
		BindingRequest bindingRequest = new BindingRequest();
		bindingRequest.setApp_guid("app_id");
		bindingRequest.setPlan_id("plan1");
		bindingRequest.setService_id("dummy_service_id");
		
		HttpEntity<BindingRequest> request = new HttpEntity<>(bindingRequest);
		
		//TODO what is instanceId
		HttpEntity<BindingResponse> response = restAuthTemplate.exchange("http://localhost:8080/v2/service_instances/instanceId/service_bindings/service1", HttpMethod.PUT, request, BindingResponse.class);
		BindingResponse bindingResponse = response.getBody();
		HashMap<String, String> credentials = (HashMap<String, String>) bindingResponse.getCredentials();
		Assert.assertEquals("localhost:8080/restmap/service1", credentials.get("url"));
		
		KeyValue keyValue = new KeyValue();
		keyValue.setKey("test-key");
		keyValue.setValue("test-value");
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("username", credentials.get("username"));
		headers.add("password", credentials.get("password"));
		HttpEntity<KeyValue> keyValueRequest = new HttpEntity<>(keyValue, headers);
		
		restTemplate.exchange("http://localhost:8080/restmap/service1", HttpMethod.POST, keyValueRequest, String.class);

		HttpEntity<KeyValue> returnKeyValue = new HttpEntity<>(headers);
		
		Assert.assertEquals("test-value", restTemplate.exchange("http://localhost:8080/restmap/service1/test-key", HttpMethod.GET, returnKeyValue, String.class).getBody());
		
		
		//Test Unbinding
		restAuthTemplate.delete("http://localhost:8080/v2/service_instances/instanceId/service_bindings/service1");
		
		try{
			restTemplate.exchange("http://localhost:8080/restmap/service1/test-key", HttpMethod.GET, returnKeyValue, String.class);
		}
		catch(HttpClientErrorException e){
			Assert.assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
		}
	}
	

}
