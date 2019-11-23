package org.smr;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.*;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
@EnableAutoConfiguration
public class GatewayApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication .class).run(args);

    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }
}
