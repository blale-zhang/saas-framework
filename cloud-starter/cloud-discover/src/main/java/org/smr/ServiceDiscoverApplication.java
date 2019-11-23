package org.smr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceDiscoverApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceDiscoverApplication.class, args);
    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }
}
