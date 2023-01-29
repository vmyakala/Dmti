/*import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.Repo.JPA_DB.DMTI_Model.DMTI_Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DmtiPractice {
	
	public static void main(String args[]) throws JSONException, JsonMappingException, JsonProcessingException 
	{
		String searchAddress = "midland";
		Date currentTime=new Date(Thu, 07 Nov 2019 15:02:53 GMT);
		//getAddressMatch(searchAddress);
		System.out.println(addHoursToJavaUtilDate(currentTime, 8));
		Date currentTime = new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(currentTime);
	    calendar.add(Calendar.MILLISECOND, 8);
	    calendar.getTime();
		
	    
	    do {
			System.out.println("gets token");
			calendar.add(Calendar.MILLISECOND, 8);
			calendar.getTime();
		}while(currentTime.after(calendar.getTime()));
		
	}
	
	
	public static void getAddressMatch(String searchAddress) throws JsonMappingException, JsonProcessingException, JSONException {
		List<Object> matchingAddresses = new ArrayList<Object>();
		String url = "https://lhrecognition.dmtispatial.com/services/rest/V2.2/suggest";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "bearer jg0CDLtl3Z9gegCOWXYC0JMaC59tOAaf");
		//System.out.println("this is token="+token);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("address", searchAddress);
		builder.queryParam("includePostalCode", "true");
		builder.queryParam("format", "json");
		builder.queryParam("returnAliases", "true");
		builder.queryParam("limit", "50");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> addressMatchResponse= restTemplate.postForEntity(builder.toUriString(), entity, Object.class);
		matchingAddresses.add(addressMatchResponse.getBody());
		System.out.println(matchingAddresses);
		
	}
	
	public static Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}
	
}
*/

// content in branch two