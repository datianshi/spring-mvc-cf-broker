package io.pivotal.pso.client.service;

import static io.pivotal.pso.client.util.VcapUtil.CREDENTIAL;
import io.pivotal.pso.client.domain.KeyValue;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapService {
	String url;
	RestTemplate template;
	Logger logger = LoggerFactory.getLogger(MapService.class);
	
	public MapService(){
		template = new RestTemplate();
		url = "http://" + CREDENTIAL.getUrl();
	}
	
	public void putKeyValue(KeyValue value){
		HttpEntity<KeyValue> request = new HttpEntity<KeyValue>(value, getSecurityHeader());
		template.exchange(url, HttpMethod.POST, request, String.class);
	}
	
	public String getValue(String key){
		HttpEntity<KeyValue> request = new HttpEntity<KeyValue>(getSecurityHeader());
		return template.exchange(url + "/" + key, HttpMethod.GET, request, String.class).getBody();
	}
	
	public Map<String, String> getKeyValues(){
		HttpEntity<KeyValue> request = new HttpEntity<KeyValue>(getSecurityHeader());
		return template.exchange(url, HttpMethod.GET, request, Map.class).getBody();
	}
	
	private HttpHeaders getSecurityHeader(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("username", CREDENTIAL.getUsername());
		headers.set("password", CREDENTIAL.getPassword());
		return headers;
	}
}
