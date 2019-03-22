package org.smr.ministore.cloud.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAdminServer
public class CloudAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudAdminApplication .class).web(true).run(args);
    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }

}
