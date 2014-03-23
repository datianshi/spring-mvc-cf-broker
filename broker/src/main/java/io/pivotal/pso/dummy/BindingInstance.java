package io.pivotal.pso.dummy;

public class BindingInstance {
	
	String username;
	String password;
	String instanceId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BindingInstance)){
			return false;
		}
		BindingInstance bi = (BindingInstance) obj;
		return instanceId != null && instanceId.equals(bi.getInstanceId());
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	
}
