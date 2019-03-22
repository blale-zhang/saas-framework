package org.smr.service.discover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;



@EnableEurekaServer
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDiscoverApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceDiscoverApplication .class).web(true).run(args);

    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }
}
