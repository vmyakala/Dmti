package com.Repo.JPA_DB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.MatchesPattern;
import javax.validation.Valid;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Repo.JPA_DB.DMTI_Model.DMTI_Response;
import com.Repo.JPA_DB.DMTI_Model.Input;
import com.Repo.JPA_DB.DMTI_Model.LocationCandidate;
import com.Repo.JPA_DB.DMTI_Model.ResponseItem;
import com.Repo.JPA_DB.DMTI_Model.SuggestionResponse;
import com.Repo.JPA_DB.DMTI_Model.SuggestionResponses;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("dmti")

public class DmtiController {
	
	@Autowired
	DmtiAction dmtiAction;
	@Autowired
	DmtiRepo repo;
	
	
	
	@PostMapping(value="/populateAddress", produces = "application/json")
	public List<TngPropertyAddress> populateAddress(@Valid @RequestBody Input body ) throws Exception
	{
		return dmtiAction.populateAddress(body);
		 
	}
	
	@GetMapping(value="/retrievedata/{var}",  produces = "application/json")
	public DMTI_Response  getAddress(@PathVariable("var") String var) throws Exception {
		List<TngPropertyAddress> data = new ArrayList<TngPropertyAddress>();
		DMTI_Response dmtiResponse = new DMTI_Response();
		
			try 
			{
				if(var.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")) {
					System.out.println("if");
					data.add(dmtiAction.findByUaid(var));
					
				}else{
					System.out.println("else");
					data.addAll(dmtiAction.findByAddress(var));
				};
				dmtiResponse.setData(data);
			}
			catch (Exception e)
			{
				// TODO: handle exception
				throw new Exception("Search with '" +var+ "' not Found");
			}
			
			return dmtiResponse;
		
	}
	
	@GetMapping(value="/lookup/{searchAddress}", produces = "application/json")
	public List<String> getAddressMatch(@PathVariable String searchAddress) throws JSONException, JsonMappingException, JsonProcessingException{

		//SuggestionResponses suggestionsList = new SuggestionResponses();
		//suggestionsList.setData(dmtiAction.getAddressMatch(searchAddress));
		return dmtiAction.getAddressMatch(searchAddress);
	}
	
	
	  @GetMapping(value="/retriveCompleteData/{completeAddress}", produces = "application/json")
	  public List<TngPropertyAddress> retrieveCompleteDataFromDmti(@PathVariable String completeAddress) throws Exception
	  {
	  
		  return dmtiAction.getCompleteData(completeAddress);
	  }
}
