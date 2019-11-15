package com.Repo.JPA_DB.DMTI_Model;

public class SuggestionResponse {
	
	private int matchCount;
	private String suggestion;
	private String aliasSuggest;
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getAliasSuggest() {
		return aliasSuggest;
	}
	public void setAliasSuggest(String aliasSuggest) {
		this.aliasSuggest = aliasSuggest;
	}
	
	

}
