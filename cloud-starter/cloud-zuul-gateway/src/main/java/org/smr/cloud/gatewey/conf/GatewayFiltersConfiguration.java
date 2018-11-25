package org.smr.cloud.gatewey.conf;


import org.smr.cloud.gateway.filter.ApiVersionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFiltersConfiguration {

    @Bean
    public ApiVersionFilter initApiVersionFilter(){
        return new ApiVersionFilter();
    }



}
