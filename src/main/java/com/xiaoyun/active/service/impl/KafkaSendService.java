package com.xiaoyun.active.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author al
 * @date 2019-7-12
 * @Desc kafka-stream发送消息的服务工具
 */
@EnableBinding(Source.class)
public class KafkaSendService {

	@Autowired
	private Source source;

	public void sendMessage(String msg) {
		try {
			source.output().send(MessageBuilder.withPayload(msg).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
