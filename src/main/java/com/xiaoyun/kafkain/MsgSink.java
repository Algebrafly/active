//package com.xiaoyun.kafkain;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//
///**
// * @author al
// * @date 2019-07-12
// * @desc kafka消息监听类
// */
//@EnableBinding(Sink.class)
//@Slf4j
//public class MsgSink {
//
//	@StreamListener(Sink.INPUT)
//	public void messageSink(Object payload) {
//		log.info("Received-Msg: {}" , payload);
//	}
//
////	@StreamListener(Sink.INPUT)
////	public void receiver(Message<Object> message) {
////		Object obj = message.getPayload();
////		System.out.println("接受对象:" + obj);
////	}
//
//}
