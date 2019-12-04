package ru.ashabalin.account.service.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ashabalin.account.service.utils.AopOrder;

@Configuration
@EnableCaching(order = AopOrder.CACHE_ADVICE)
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }

}
