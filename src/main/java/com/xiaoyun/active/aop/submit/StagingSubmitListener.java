package com.xiaoyun.active.aop.submit;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author al
 * @date 2019/6/5 13:52
 * @description
 */
@Component
@Slf4j
public class StagingSubmitListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        if("take".equals(eventName)){
            log.info("[-提交到申请分期节点-]调取易分期申请分期接口（异步）------->");



        }
    }
}
