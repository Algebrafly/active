package com.xiaoyun.active.controller;

import org.activiti.bpmn.model.*;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author al
 * @date 2019/6/6 10:05
 * @description
 */
@RestController
public class NodeOprController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/getNodeInfo", method = RequestMethod.GET)
    public void getNodeInfo(@RequestParam String procDefId){
        BpmnModel model = repositoryService.getBpmnModel(procDefId);
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for (FlowElement e : flowElements) {

                if (e instanceof UserTask) {
                    System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());

                    UserTask userTask = (UserTask) e;
                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        System.out.println(sequenceFlow.getId() + " -条件：" + sequenceFlow.getConditionExpression() + " -SRC:"
                                + sequenceFlow.getSourceRef() + " -DST:" + sequenceFlow.getTargetRef() + "-");

                    }
                }

                if (e instanceof StartEvent) {
                    System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());

                    StartEvent userTask = (StartEvent) e;
                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        System.out.println(sequenceFlow.getId() + " -条件：" + sequenceFlow.getConditionExpression() + " -SRC:"
                                + sequenceFlow.getSourceRef() + " -DST:" + sequenceFlow.getTargetRef() + "-");

                    }
                }

                if (e instanceof EndEvent) {
                    System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());

                    EndEvent userTask = (EndEvent) e;
                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        System.out.println(sequenceFlow.getId() + " -条件：" + sequenceFlow.getConditionExpression() + " -SRC:"
                                + sequenceFlow.getSourceRef() + " -DST:" + sequenceFlow.getTargetRef() + "-");

                    }
                }
            }
        }

    }

}
