package org.smr.comp.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RedisStarterApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(RedisStarterApplication .class).web(true).run(args);
    }

    public int getExitCode() {
        System.out.println(getClass().getName() + " is exit!");
        return 0;
    }

}
