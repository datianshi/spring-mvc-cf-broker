package io.pivotal.pso.dummy;

import io.pivotal.pso.domain.BindingRequest;
import io.pivotal.pso.domain.BindingResponse;
import io.pivotal.pso.domain.Catalog;
import io.pivotal.pso.domain.Plan;
import io.pivotal.pso.domain.ProvisionRequest;
import io.pivotal.pso.domain.Service;
import io.pivotal.pso.service.ProvisionService;
import io.pivotal.pso.util.JSONUtil;
import io.pivotal.pso.util.VcapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The dummy service holds a map of maps based on the service id as the key.
 * Whenever the provision method was called. There will be a new map created to represent
 * a restful service as a cloudfoundry service for demo purpose.
 * 
 * @author Shaozhen Ding
 */
@Component
public class DummyProvisionService implements ProvisionService, DummyRestService{
	
	public final static Catalog STATIC_CATALOG;
	
	Logger logger = LoggerFactory.getLogger(DummyProvisionService.class);
	
	static{
		STATIC_CATALOG = new Catalog();
		List<Service> services = new ArrayList<>();
		STATIC_CATALOG.setServices(services);
		
		Service service = new Service();
		services.add(service);
		
		List<Plan> plans = new ArrayList<>();
		
		Plan plan1 = new Plan();
		plan1.setId(UUID.randomUUID().toString());
		plan1.setDescription("Small Plan");
		plan1.setName("plan1");
		
		Plan plan2 = new Plan();
		plan2.setId(UUID.randomUUID().toString());
		plan2.setDescription("Big Plan");
		plan2.setName("plan2");
		
		plans.add(plan1);
		plans.add(plan2);
		
		service.setPlans(plans);
		
		service.setId(UUID.randomUUID().toString());
		service.setName("dummy_service_name");
		service.setDescription("Dummy service to mimic how service broker works");
		service.setBindable(true);
	}
	
	private Map<String, ProvisionObject> servicesStorages;
	
	public DummyProvisionService(){
		servicesStorages = new HashMap<>();
	}

	@Override
	public void provision(String id, ProvisionRequest request) {
		Map<String, String> inMemoryKV = new HashMap<>();
		ProvisionObject pi = new ProvisionObject();
		pi.setInMemoryKV(inMemoryKV);
		servicesStorages.put(id, pi);
	}
	
	@Override
	public void deprovision(String id) {
		servicesStorages.remove(id);
	}	
	
	
	public void putKV(String id, String key, String value){
		if(servicesStorages.get(id) == null){
			throw new IllegalArgumentException("The id: " + id + " is a not valid service");
		}
		servicesStorages.get(id).getInMemoryKV().put(key, value);
	}
	
	public String getValue(String id, String key){
		return servicesStorages.get(id).getInMemoryKV().get(key);
	}
	
	public ProvisionObject getProvisionObject(String id){
		return servicesStorages.get(id);
	}

	@Override
	public Catalog getCatalog() {
		return STATIC_CATALOG;
	}

	@Override
	public BindingResponse bind(String instanceId, String bindingId, BindingRequest request) {
		
		
		ProvisionObject pi = servicesStorages.get(instanceId);
		BindingInstance bindingInstance = new BindingInstance();
		String username = UUID.randomUUID().toString();
		String password = UUID.randomUUID().toString();
		bindingInstance.setBindingId(bindingId);
		bindingInstance.setPassword(password);
		bindingInstance.setUsername(username);
		pi.addBindingInstance(bindingInstance);
		
		Credential credential = new Credential();
		BindingResponse bindingResponse = new BindingResponse();
		bindingResponse.setCredentials(credential);	
		
		credential.setUrl(JSONUtil.getHostNameFromVcapApplication(VcapUtil.VCAP_APPLICATION) + "/restmap/" + instanceId);
		credential.setUsername(username);
		credential.setPassword(password);
		
		return bindingResponse;
	}

	@Override
	public void unbind(String id, String instanceId) {
		ProvisionObject po = servicesStorages.get(id);
		if(po == null){
			logger.info("service with id: " + id + " does not exists");
		}
		else{
			servicesStorages.get(id).removeBindingInstance(instanceId);
		}
	}

	@Override
	public boolean authenticate(String id, String username,
			String password) {
		if (username == null || password == null) {
			return false;
		}
		return servicesStorages.get(id).isAuthenticate(username, password);
	}

	@Override
	public Map<String, String> getKVs(String id) {
		ProvisionObject po = servicesStorages.get(id);
		if(po == null){
			logger.info("service with id: " + id + " does not exists");
			return null;
		}
		else{
			return servicesStorages.get(id).getInMemoryKV();
		}
	}

}
