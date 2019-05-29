package com.xiaoyun.active.aop;

import org.activiti.engine.delegate.*;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @program: active
 * @description:
 * @author: firstSeven
 * @create: 2019/05/15
 */
@Component
@SuppressWarnings("serial")
public class firstAspect  implements ExecutionListener{

    /**
    *@Description: 拦截器
    *@Param: [delegateExecution]
    *@return: void
    *@Author: firstSeven
    *@date: 2019/5/17
    */
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String eventName = delegateExecution.getEventName();
        //业务id
        String processBusinessKey = delegateExecution.getProcessBusinessKey();
        System.out.println(processBusinessKey);
        //流程定义id
        String processDefinitionId = delegateExecution.getProcessDefinitionId();
        System.out.println(processDefinitionId);
        //activeName
        String currentActivityName = delegateExecution.getCurrentActivityName();
        System.out.println(currentActivityName);
        //多次尝试发现无论如何在审批过程中都会走take 即使配置start end take触发三次 但是还是都走了take
        if (eventName.equals("start")){
            System.out.println("start");
        }else if (eventName.equals("end")){
            System.out.println("end");
        }else if (eventName.equals("take")){
            System.out.println("take");
        }
    }

}
