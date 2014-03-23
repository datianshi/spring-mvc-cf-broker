/**
 * ProvisionService is to provision a new service based on the id provided
 * @author Shaozhen Ding
 */
package io.pivotal.pso.service;

import io.pivotal.pso.domain.BindingRequest;
import io.pivotal.pso.domain.BindingResponse;
import io.pivotal.pso.domain.Catalog;
import io.pivotal.pso.domain.ProvisionRequest;

public interface ProvisionService {
	void provision(String id, ProvisionRequest request);
	void deprovision(String id);
	Catalog getCatalog();
	BindingResponse bind(String id, String instanceId, BindingRequest request);
	void unbind(String id, String instanceId);
}
