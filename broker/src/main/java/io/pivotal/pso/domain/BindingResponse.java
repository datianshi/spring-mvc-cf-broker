package io.pivotal.pso.domain;

import java.io.Serializable;

public class BindingResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Object credentials;
	String syslog_drain_url;
	
	
	public Object getCredentials() {
		return credentials;
	}
	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}
	public String getSyslog_drain_url() {
		return syslog_drain_url;
	}
	public void setSyslog_drain_url(String syslog_drain_url) {
		this.syslog_drain_url = syslog_drain_url;
	}

}
