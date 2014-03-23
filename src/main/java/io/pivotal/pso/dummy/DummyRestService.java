package io.pivotal.pso.dummy;

public interface DummyRestService {
	void putKV(String id, String key, String value);
	String getValue(String id, String key);
	boolean authenticate(String id, String username, String password);
}
