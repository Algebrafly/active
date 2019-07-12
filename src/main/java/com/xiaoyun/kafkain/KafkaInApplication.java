//package com.xiaoyun.kafkain;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//
//@SpringBootApplication(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//        org.activiti.spring.boot.SecurityAutoConfiguration.class,
//        DataSourceAutoConfiguration.class,
//        DruidDataSourceAutoConfigure.class
//})//排除security 安全校验
//public class KafkaInApplication {
//
//    public static void main(String[] args) {
//        String[] args2 = new String[]{"--server.port=8182","--spring.profiles.active=in1"};
//        SpringApplication.run(KafkaInApplication.class, args2);
//    }
//
//}
