package io.pivotal.pso.dummy;

import io.pivotal.pso.domain.ProvisionRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProvisionObject {
	Map<String, String> inMemoryKV;
	ProvisionRequest request;
	
	List<BindingInstance> bindingInstances = new ArrayList<>();
	
	public Map<String, String> getInMemoryKV() {
		return inMemoryKV;
	}
	public void setInMemoryKV(Map<String, String> inMemoryKV) {
		this.inMemoryKV = inMemoryKV;
	}
	
	public ProvisionRequest getRequest() {
		return request;
	}
	public void setRequest(ProvisionRequest request) {
		this.request = request;
	}
	
	public List<BindingInstance> getBindingInstances(){
		return bindingInstances;
	}
	
	public void addBindingInstance(BindingInstance bindingInstance){
		bindingInstances.add(bindingInstance);
	}
	
	public void removeBindingInstance(String instanceId){
		for(BindingInstance bindingInstance : bindingInstances){
			if(bindingInstance.getBindingId().equals(instanceId)){
				bindingInstances.remove(bindingInstance);
				break;
			}
		}
	}
	
	public BindingInstance getBindingInstance(String instanceId){
		for(BindingInstance bindingInstance : bindingInstances){
			if(bindingInstance.getBindingId().equals(instanceId)){
				return bindingInstance;
			}
		}
		return null;
	}
	
	public boolean isAuthenticate(String username, String password){
		for(BindingInstance bindingInstance : bindingInstances){
			
			if(username.equals(bindingInstance.getUsername()) && password.equals(bindingInstance.getPassword())){
				return true;
			}
		}
		return false;
	}	
	
}
