package com.Repo.JPA_DB;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.Repo.JPA_DB.DMTI_Model.DmtiToken;

@Service
public class TokenAction {
	@Autowired 
	private Environment env;
	//@CacheEvict(value="eightHour", key="#keyValue", beforeInvocation = true)
		
		@Cacheable(value="eightHour", key="#keyValue")
		public ResponseEntity<DmtiToken> getToken(int keyValue){
			System.out.println("Retrieving token from DMTI server for name: " + keyValue);
			String url = env.getProperty("tokenUrl");
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.queryParam("client_id", env.getProperty("clientId"));
			builder.queryParam("client_secret", env.getProperty("clientSecret"));
			builder.queryParam("grant_type", env.getProperty("grantType"));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<DmtiToken> response= restTemplate.postForEntity(builder.toUriString(), entity, DmtiToken.class);

			return response;
		}
		
		
		
}
