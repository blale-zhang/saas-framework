package org.smr.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class CloudzipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudzipkinApplication.class, args);
    }

}
