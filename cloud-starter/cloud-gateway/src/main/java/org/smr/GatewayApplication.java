package org.smr;
import org.smr.ministore.gateway.filter.SimpleFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication .class).web(true).run(args);

    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }

    @Bean
    public SimpleFilter simpleFilter() {
        return new SimpleFilter();
    }
}
