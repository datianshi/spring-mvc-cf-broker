package io.pivotal.pso.client;

import io.pivotal.pso.client.domain.KeyValue;
import io.pivotal.pso.client.service.MapService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MapController {
	
	@Autowired
	MapService mapService;
	
	Logger logger = LoggerFactory.getLogger(MapController.class);
	
	@RequestMapping(value = "/map", method=RequestMethod.GET)
	public String getKey(Model model){
		model.addAttribute("keyValue", new KeyValue());
		model.addAttribute("keyValues", mapService.getKeyValues());
		return "map";
	}
	
	@RequestMapping(value = "/map", method=RequestMethod.POST)
	public String putKeyValue(@ModelAttribute("keyValue") KeyValue keyValue){
		mapService.putKeyValue(keyValue);
		return "redirect:map";
	}
	
	@RequestMapping(value = "/env", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getEnv(){
		return System.getenv();
	}	
	
	@RequestMapping(value = "/getKey/{key}", method=RequestMethod.POST)
	public @ResponseBody String putKeyValue(@PathVariable String key){
		return mapService.getValue(key);
	}
	
	
}
