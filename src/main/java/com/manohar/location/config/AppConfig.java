package com.manohar.location.config;

import com.manohar.location.service.LocationStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Manohar
 * Project Name: location-search
 */
@Configuration
public class AppConfig {
    @Bean
    public LocationStore locationStore() {
        return new LocationStore();
    }
}
