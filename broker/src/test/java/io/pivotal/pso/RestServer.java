package io.pivotal.pso;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class RestServer {
	
	private static Server server;
	
	public static void start() throws Exception{
        
		System.setProperty("VCAP_APPLICATION", "{\"instance_id\": \"sfasdfasf\", \"application_uris\" : [\"localhost:8080\"]}");
		server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
 
        server.setHandler(context);
        server.start();
//        server.join();
	}
	
	public static void stop() throws Exception{
		server.stop();
	}
	
	public static void main(String[] args) throws Exception{
		RestServer.start();
	}
}
