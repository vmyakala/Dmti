package com.Repo.JPA_DB.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.Repo.JPA_DB.DmtiApplication;




import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class DmtiCacheConfig extends CachingConfigurerSupport 
{
	/*
	 * @Bean public EhCacheManagerFactoryBean cacheMangerFactory() {
	 * EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
	 * bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
	 * bean.setShared(true); return bean; }
	 */
	    
	@Bean
	    public net.sf.ehcache.CacheManager ehCacheManager(){
		
	    	CacheConfiguration ehCache = new CacheConfiguration();
	    	ehCache.setName("eightHour");
	    	ehCache.setTimeToLiveSeconds(28000);
	    	ehCache.setMemoryStoreEvictionPolicy("LRU");
	    	ehCache.setMaxEntriesLocalHeap(1000);
	    	
	    	net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
	    	config.addCache(ehCache);
	    	return net.sf.ehcache.CacheManager.newInstance(config);
	    	
	    }
	        
	@Bean
	    @Override
	    public CacheManager cacheManager() {
	        return new EhCacheCacheManager(ehCacheManager());
	    }
}
