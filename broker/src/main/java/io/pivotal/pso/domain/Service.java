package io.pivotal.pso.domain;

import java.util.List;

public class Service {
	String id;
	String name;
	String description;
	boolean bindable;
	
	public boolean isBindable() {
		return bindable;
	}
	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}
	
	List<Plan> plans;
	
	public List<Plan> getPlans() {
		return plans;
	}
	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
