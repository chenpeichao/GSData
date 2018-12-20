package org.pcchen.uar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序启动类
 *
 * @author cpc
 * @create 2018-12-19 15:56
 **/
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
@PropertySource(value = {"classpath:config/constant/constant.properties"}, encoding = "utf-8")
public class GSDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(GSDataApplication.class, args);
    }
}
