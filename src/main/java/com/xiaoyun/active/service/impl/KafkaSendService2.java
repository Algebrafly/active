//package com.xiaoyun.active.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * @author al
// * @date 2019/7/12 10:51
// * @description
// */
//@Component
//@Slf4j
//public class KafkaSendService2 {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Value("${activiti.kafka_topic}")
//    private String kafkaTopic;
//
//    public void sendMessage(String msg){
//        kafkaTemplate.send(kafkaTopic,msg);
//    }
//
//}
