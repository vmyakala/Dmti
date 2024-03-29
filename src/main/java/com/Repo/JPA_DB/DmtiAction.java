package com.Repo.JPA_DB;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.Repo.JPA_DB.DMTI_Model.DmtiToken;
import com.Repo.JPA_DB.DMTI_Model.Input;
import com.Repo.JPA_DB.DMTI_Model.LocationCandidate;
import com.Repo.JPA_DB.DMTI_Model.ResponseItem;
import com.Repo.JPA_DB.DMTI_Model.SuggestionResponse;
import com.Repo.JPA_DB.DMTI_Model.SuggestionResponses;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Service
public class DmtiAction {
	
	@Autowired
	TokenAction tokenAction;
	@Autowired
	DmtiRepo repo;
	SuggestionResponses suggestionsList = new SuggestionResponses();
	@Autowired 
	private Environment env;
	 
	
	public List<TngPropertyAddress> populateAddress(Input body) throws Exception{
		
		List<ResponseItem> responseItems =  new ArrayList<ResponseItem>();
		List<TngPropertyAddress> data = new ArrayList<TngPropertyAddress>();
		//System.out.println( locCan.getResponseItems().get(0) );
		responseItems  = body.getResponseItems();
		
		
		for (ResponseItem responseItem : responseItems) {
			//System.out.println(locCan.size());
			populateAddressAction(responseItem.getLocationCandidates().get(0));
			data.addAll(repo.findByAddress(responseItem.getLocationCandidates().get(0).getAddressLine()));
		} 
		return data;
			
	}
	
	//Checks the Uaid in local DB and if data is already present it updates or if no data than it inserts 
	public void populateAddressAction(final LocationCandidate locCan) throws Exception  {
		 Date dateobj = new Date();
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		TngPropertyAddressPK tngpk = new TngPropertyAddressPK() ;
		TngPropertyAddress tng = new TngPropertyAddress() ;
			try {
				
				//System.out.println(locCan.getUAID());
				if(locCan.getUAID().isEmpty()) {
					throw new Exception("UAID Cannot be empty");
				}else {
					tngpk.setDmtiUaid(locCan.getUAID());
				}
				
				if(locCan.getSubAddress()==null||locCan.getSubAddress().getUUAID()==null) {
					tngpk.setDmtiUuaid("");
				}else {
					tngpk.setDmtiUuaid(locCan.getSubAddress().getUUAID());
				}
				tng.setId(tngpk);
				tng.setAddress(locCan.getAddressLine());
				tng.setCity(locCan.getMunicipality().getValue().toString());
				tng.setCreatedDate(dateobj);
				tng.setDmtiMafId(0);
				tng.setDmtiRdsId(0);
				tng.setLast_updated(time);
				tng.setLatitude(new BigDecimal(locCan.getCoordinates().get(0).getLat()));
				tng.setLongitude(new BigDecimal(locCan.getCoordinates().get(0).getLon()));
				tng.setMultiDwellingUnit(0);
				tng.setNasPauid("");
				tng.setNasPropertyAddressId(0);
				tng.setPositionDeterminationCode(locCan.getCoordinates().get(0).getPDC());
				tng.setPostalCode(locCan.getPostalCode().getValue());
				tng.setPrimaryUse(0);
				tng.setProvince(locCan.getStateProvince().getValue().toString());
				//tng.setStreetDirection(locCan.getStreetDirection().getValue().toString());
				if(locCan.getStreetDirection().getValue()==null) {
					tng.setStreetDirection(null);
				}else {
					tng.setStreetDirection(locCan.getStreetDirection().getValue().toString());
				}
				
				tng.setStreetName(locCan.getStreetName().getValue());
				if(locCan.getStreetNumberSuffix().getValue()==null) {
					tng.setStreetNumSuffix(null);
				}else {
					tng.setStreetNumSuffix(locCan.getStreetNumberSuffix().getValue().toString());
				}
								
				tng.setStreetNumber(locCan.getStreetNumber().getValue());
				
				if(locCan.getStreetPreDirection().getValue()==null) {
					tng.setStreetPreDirection(null);
				}else {
					tng.setStreetPreDirection(locCan.getStreetPreDirection().getValue().toString());
				}
				
				if(locCan.getStreetPreType().getValue()==null) {
					tng.setStreetPreType(null);
				}else {
					tng.setStreetPreType(locCan.getStreetPreType().getValue().toString());
				}
				
				if(locCan.getStreetType().getValue()==null) {
					tng.setStreetType(null);
				}else {
					tng.setStreetType(locCan.getStreetType().getValue().toString());
				}
				tng.setUnitCorroborativeIndex(0);
				tng.setUnitNumber(null);
				tng.setUnitType(null);
				
				repo.save(tng);	
				
			}catch (Exception e) {
				
				throw new Exception(e.getMessage());
			}
			
				
		
	}

	
	
	
	/***************************************************************************************************************/
	
	
	public TngPropertyAddress findByUaid(String uaid) {
		
		TngPropertyAddressPK tngpk = new TngPropertyAddressPK() ;	
		tngpk.setDmtiUaid(uaid);
		tngpk.setDmtiUuaid("");
		
		Optional<TngPropertyAddress> aa = repo.findById(tngpk );
		return aa.ofNullable(aa.get()).orElseGet(null); 
		
	}
	
	
	
	public List<TngPropertyAddress> findByAddress(String address) throws JSONException {
		List<TngPropertyAddress> data = new ArrayList<TngPropertyAddress>();
		data.addAll(repo.findByAddress(address));
		return data;
	}
	
	
	
	private static ResponseEntity<DmtiToken> token_response;
	
	

	public List<String> getAddressMatch(String searchAddress) throws JsonMappingException, JsonProcessingException, JSONException {
		
		List<SuggestionResponse> matchingAddresses = new ArrayList<SuggestionResponse>();
		List<String> sugList = new ArrayList<String>();
		/*
		 * if (token_response == null ||
		 * token_response.getBody().getExpiryTime().before(new Date()) ) {
		 * token_response = getToken(); }
		 */
		token_response = tokenAction.getToken(8);
		
		matchingAddresses.addAll(Arrays.asList(getAddressMatchAction(searchAddress)));
		
		for (SuggestionResponse suggestionResponse : matchingAddresses) {
			
			if(suggestionResponse.getMatchCount()<=Integer.parseInt(env.getProperty("suggestionMatchCount"))) {
				sugList.add(suggestionResponse.getSuggestion());
			}
			
			
		}
		
		return sugList;
	}

	public SuggestionResponse[] getAddressMatchAction(String searchAddress) {
		
		String url = env.getProperty("addressSuggestionsUrl");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token_response.getBody().getToken_type()+" "+token_response.getBody().getAccess_token());
		System.out.println("this is token="+token_response.getBody().getToken_type()+" "+token_response.getBody().getAccess_token());
		URIBuilder ub;
		String tempurl="";
		
		
		
		try {
			ub = new URIBuilder(url);
			ub.addParameter("address", searchAddress);
			ub.addParameter("includePostalCode", env.getProperty("includePostalCode"));
			ub.addParameter("Accept", MediaType.APPLICATION_JSON_VALUE);
			ub.addParameter("returnAliases", env.getProperty("returnAliases"));
			ub.addParameter("limit", env.getProperty("suggestionsLimit"));
			tempurl = ub.toString(); 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SuggestionResponse[]> addressMatchResponse= restTemplate.postForEntity(tempurl, entity, SuggestionResponse[].class) ;
		
		return addressMatchResponse.getBody();
	}
	

	
	public List<TngPropertyAddress> getCompleteData(String completeAddress) throws Exception {
		List<TngPropertyAddress> data = new ArrayList<TngPropertyAddress>();
			data.addAll(repo.findByAddress(completeAddress));
			if(data.isEmpty()) {
			/*
			 * if (token_response == null ||
			 * token_response.getBody().getExpiryTime().before(new Date()) ) {
			 * token_response = getToken(); }
			 */
				token_response = tokenAction.getToken(8);
				String url = env.getProperty("recognizeFreeFormUrl");
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", token_response.getBody().getToken_type()+" "+token_response.getBody().getAccess_token());
				headers.set("Access-Control-Allow-Origin", env.getProperty("Access-Control-Allow-Origin"));
				URIBuilder ub;
				String tempurl="";
				try {
					ub = new URIBuilder(url);
					ub.addParameter("address", completeAddress);
					ub.addParameter("InferSubAddresses", env.getProperty("InferSubAddresses"));			
					ub.addParameter("scoreThreshold", env.getProperty("scoreThreshold"));
					ub.addParameter("limit", env.getProperty("dmtiAddressDetailsLimit"));
					ub.addParameter("Accept", MediaType.APPLICATION_JSON_VALUE);
					tempurl = ub.toString(); 
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				HttpEntity<String> entity = new HttpEntity<>(headers);
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Input> addressMatchResponse = restTemplate.postForEntity(tempurl, entity, Input.class);
				
				data.addAll(populateAddress(addressMatchResponse.getBody()));
			}			
		
		return data;
		
	}
	
	
	
	
	
	
}