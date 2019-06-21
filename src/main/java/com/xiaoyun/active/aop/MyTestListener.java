package com.xiaoyun.active.aop;

import com.xiaoyun.active.controller.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author al
 * @date 2019/6/21 13:11
 * @description
 */
@Component
@Slf4j
public class MyTestListener implements TaskListener {

    private TestService testService;

    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("自定义测试任务监听器 ------------------->>>测试外部服务注入");
        testService = SpringContextHolder.getBean(TestService.class);
        log.info(testService.testGet("Lily"));


        String eventName= delegateTask.getEventName();

        if("create".equals(eventName)){
            log.info("create事件触发-------------->>");
        }


    }
}
