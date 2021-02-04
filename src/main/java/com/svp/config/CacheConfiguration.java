package com.svp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.svp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.svp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.svp.domain.User.class.getName());
            createCache(cm, com.svp.domain.Authority.class.getName());
            createCache(cm, com.svp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.svp.domain.Company.class.getName());
            createCache(cm, com.svp.domain.Company.class.getName() + ".locations");
            createCache(cm, com.svp.domain.Professional.class.getName());
            createCache(cm, com.svp.domain.Customer.class.getName());
            createCache(cm, com.svp.domain.Customer.class.getName() + ".requests");
            createCache(cm, com.svp.domain.Customer.class.getName() + ".choices");
            createCache(cm, com.svp.domain.CompanyType.class.getName());
            createCache(cm, com.svp.domain.SubscriptionType.class.getName());
            createCache(cm, com.svp.domain.ProfessionalDetails.class.getName());
            createCache(cm, com.svp.domain.CompanyLocation.class.getName());
            createCache(cm, com.svp.domain.CompanyLocation.class.getName() + ".professionals");
            createCache(cm, com.svp.domain.ProRequest.class.getName());
            createCache(cm, com.svp.domain.ProChoice.class.getName());
            createCache(cm, com.svp.domain.Rating.class.getName());
            createCache(cm, com.svp.domain.ProfessionalAudit.class.getName());
            createCache(cm, com.svp.domain.Bill.class.getName());
            createCache(cm, com.svp.domain.BillAudit.class.getName());
            createCache(cm, com.svp.domain.Hit.class.getName());
            createCache(cm, com.svp.domain.Summary.class.getName());
            createCache(cm, com.svp.domain.ProfessionalProfileImage.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
