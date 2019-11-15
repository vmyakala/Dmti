package com.Repo.JPA_DB.DMTI_Model;

import java.util.Calendar;
import java.util.Date;

public class DMTI_Token {
	
	private String token_type;
	private String access_token;
	private String expires_in;
	private Date expiryTime;
	
	public Date getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Date currentTime, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.SECOND, hours);
		this.expiryTime = calendar.getTime();
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
		setExpiryTime(new Date(), Integer.parseInt(expires_in));
	}
}
