package com.xiaoyun.active.aop;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author al
 * @date 2019/6/4 16:45
 * @description
 */
@Component
@Slf4j
public class NextTaskListener implements TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> variables = delegateTask.getVariables();
        String applyStatusCode = (String) variables.get("apply_status_code");
        String applyStatusName = (String) variables.get("apply_status_Name");
        String nextOprId = (String) variables.get("next_opr_Id");
        String nextOprName = (String) variables.get("next_opr_name");
        log.info("下一节点操作角色/人：ID = {}, name = {}",nextOprId, nextOprName);

        //业务id
        String processBusinessKey = delegateTask.getExecution().getProcessBusinessKey();
        log.info("关联的业务ID:{} ", processBusinessKey);

        String eventName = delegateTask.getEventName();
        if("create".equals(eventName)){
            log.info("[{}节点--create事件]----->",delegateTask.getName());
            delegateTask.setAssignee(nextOprId+":"+nextOprName);
            delegateTask.setDescription(applyStatusCode+":"+applyStatusName);

        } else if("complete".equals(eventName)) {
            log.info("------->[{}节点-complete事件]",delegateTask.getName());


        }

    }
}
