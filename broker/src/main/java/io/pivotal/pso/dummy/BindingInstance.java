package io.pivotal.pso.dummy;

public class BindingInstance {
	
	String username;
	String password;
	String bindingId;
	
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
		return bindingId != null && bindingId.equals(bi.getBindingId());
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBindingId() {
		return bindingId;
	}
	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}
	
	
}
