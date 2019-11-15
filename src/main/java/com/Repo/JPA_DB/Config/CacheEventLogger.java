package com.Repo.JPA_DB.Config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;



public class CacheEventLogger implements CacheEventListener<Object, Object> {
	
	@Override
	public void onEvent(
		      CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		        
		    }
}
