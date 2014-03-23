/**
 * 
 * This controller is a representation of the a provisoned resource
 * that serve all the key value request mappings.
 * 
 * @author Shaozhen Ding
 */
package io.pivotal.pso.dummy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restmap")
public class DummyController {
	
	@Autowired
	DummyRestService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void putKey(@PathVariable String id, @RequestBody KeyValue keyValue, @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password){
		authentication(id, username, password);
		service.putKV(id, keyValue.getKey(), keyValue.getValue());
	}

	@RequestMapping(value = "/{id}/{key}", method = RequestMethod.GET)
	public String putKey(@PathVariable String id, @PathVariable String key, @RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password){
		authentication(id, username, password);
		return service.getValue(id, key);
	}
	
	
	private void authentication(String id, String username, String password) {
		if(!service.authenticate(id, username, password)){
			throw new AuthenticateException();
		}
	}
}
