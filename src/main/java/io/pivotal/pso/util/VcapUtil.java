package io.pivotal.pso.util;

public class VcapUtil {
	public final static String VCAP_APPLICATION;

	static {
		VCAP_APPLICATION = System.getenv("VCAP_APPLICATION") != null ? System
				.getenv("VCAP_APPLICATION") : System
				.getProperty("VCAP_APPLICATION");
	}
}
