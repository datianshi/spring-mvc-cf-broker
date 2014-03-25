package io.pivotal.pso.client.domain;

import java.io.Serializable;

public class KeyValue implements Serializable{
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	private static final long serialVersionUID = 1L;
	String key;
	String value;
	
}
