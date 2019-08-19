package org.smr.cloud.gateway.conf;


import org.smr.cloud.gateway.filter.ApiVersionFilter;
import org.smr.cloud.gateway.filter.PostZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GatewayFiltersConfiguration {

    @Bean
    public ApiVersionFilter initApiVersionFilter(){
        return new ApiVersionFilter();
    }


/*    @Bean
    public PostZuulFilter initPostZuulFilter(){
        return new PostZuulFilter();
    }*/

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


}
