package io.pivotal.pso.domain;

import java.util.List;

public class Catalog {
	
	List<Service> services;

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
}
