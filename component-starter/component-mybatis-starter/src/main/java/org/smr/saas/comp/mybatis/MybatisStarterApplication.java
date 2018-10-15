package org.smr.saas.comp.mybatis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MybatisStarterApplication {


        public static void main(String[] args) {
            new SpringApplicationBuilder(MybatisStarterApplication.class).web(true).run(args);
        }

        public int getExitCode() {
            System.out.println(getClass().getName() + " is exit!");
            return 0;
        }

}
