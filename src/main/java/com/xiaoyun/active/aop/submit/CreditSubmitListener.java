package com.xiaoyun.active.aop.submit;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author al
 * @date 2019/6/4 9:10
 * @description
 */
@Component
@Slf4j
public class CreditSubmitListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        if("take".equals(eventName)){
            log.info("[-提交到征信查询节点-]调取征信查询接口（异步）------->");



        }


    }
}
