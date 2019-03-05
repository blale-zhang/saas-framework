package org.smr;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class GatewatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewatApplication.class, args);
    }

}
