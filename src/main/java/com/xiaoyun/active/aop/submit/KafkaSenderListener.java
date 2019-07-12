package com.xiaoyun.active.aop.submit;

import com.xiaoyun.active.aop.SpringContextHolder;
import com.xiaoyun.active.service.impl.KafkaSendService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author al
 * @date 2019/7/12 9:10
 * @description
 */
@Component
@Slf4j
public class KafkaSenderListener implements ExecutionListener {

    private KafkaSendService kafkaSendService;

    @Override
    public void notify(DelegateExecution execution) {

        kafkaSendService = SpringContextHolder.getBean(KafkaSendService.class);

        String eventName = execution.getEventName();
        if("take".equals(eventName)){
            log.info("[-资料填写节点-]kafka发送消息通知外部服务处理------->");
            kafkaSendService.sendMessage("please notify E-stage credit query!");
        }
    }
}
