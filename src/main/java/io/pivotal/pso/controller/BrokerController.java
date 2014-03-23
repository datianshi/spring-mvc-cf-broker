/**
 * This is a Broker controller trying to be compliant with  
 * http://docs.cloudfoundry.com/docs/running/architecture/services/api.html
 * @author Shaozhen Ding
 */
package io.pivotal.pso.controller;

import io.pivotal.pso.domain.BindingRequest;
import io.pivotal.pso.domain.BindingResponse;
import io.pivotal.pso.domain.Catalog;
import io.pivotal.pso.domain.ProvisionRequest;
import io.pivotal.pso.service.ProvisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class BrokerController {
	
	@Autowired
	ProvisionService provisionService;
	
	@RequestMapping("/catalog")
	public ResponseEntity<Catalog> getCatalog(){
		return new ResponseEntity<Catalog>(provisionService.getCatalog(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service_instances/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> provision(@PathVariable String id, @RequestBody ProvisionRequest request){
		provisionService.provision(id, request);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/service_instances/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deprovision(@PathVariable String id){
		provisionService.deprovision(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.PUT)
	public ResponseEntity<BindingResponse> binding(@PathVariable String instanceId, @PathVariable String id, @RequestBody BindingRequest request){
		return new ResponseEntity<BindingResponse>(provisionService.bind(id, instanceId, request), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> unbind(@PathVariable String instanceId, @PathVariable String id, 
			@RequestParam(value = "service_id", required = false) String serviceId, 
			@RequestParam(value = "plan_id", required= false) String planId){
		provisionService.unbind(id, instanceId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}	
	
	
}
