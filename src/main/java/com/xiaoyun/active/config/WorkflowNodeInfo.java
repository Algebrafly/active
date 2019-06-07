package com.xiaoyun.active.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author al
 * @date 2019/6/6 13:07
 * @description
 */
@Component
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class WorkflowNodeInfo {

    @Autowired
    private RepositoryService repositoryService;

//    @Autowired
//    INodeService nodeService;
//
//    @Autowired
//    IRouteService routeService;

    /**
     * 初始化节点 以及 路由信息
     * @param procDefId
     */
    public void getNodeInfo(String procDefId){
        BpmnModel model = repositoryService.getBpmnModel(procDefId);
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {

                if (e instanceof UserTask) {
                    UserTask userTask = (UserTask) e;

                    log.info("NodeId:" + e.getId() + "  -----Name:" + e.getName());
//                    WorkFlowNode node = new WorkFlowNode();
//                    node.setNodeId(e.getId());
//                    node.setNodeName(e.getName());
//                    node.setProcDefId(procDefId);
//                    node.setCreateAt(new Date());
//                    node.setCreateBy("system");
//                    node.setUpdateAt(new Date());
//                    nodeService.insertSelective(node);

                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        log.info("FlowId:"+sequenceFlow.getId() + "  -----条件：" + sequenceFlow.getConditionExpression() + "     -SRC:"
                                + sequenceFlow.getSourceRef() + "     -DST:" + sequenceFlow.getTargetRef() + "-");

                        String condStr = sequenceFlow.getConditionExpression();
                        String cond;
                        if(condStr == null || "".equals(condStr)){
                            cond = "0";
                        } else {
                            cond = condStr.substring(condStr.indexOf("'")+1,condStr.lastIndexOf("'"));
                        }

//                        WorkFlowRoute route = new WorkFlowRoute();
//                        route.setRouteId(sequenceFlow.getId());
//                        route.setRouteName(sequenceFlow.getName());
//                        route.setSrcNodeId(sequenceFlow.getSourceRef());
//                        route.setDstNodeId(sequenceFlow.getTargetRef());
//                        route.setProcDefId(procDefId);
//                        route.setRouteCond(cond);
//                        route.setCreateBy("system");
//                        route.setCreateAt(new Date());
//                        routeService.insertSelective(route);

                    }
                }

                if (e instanceof StartEvent) {

                    StartEvent userTask = (StartEvent) e;

                    log.info("NodeId:" + e.getId() + "  -----Name:" + e.getName());
//                    WorkFlowNode node = new WorkFlowNode();
//                    node.setNodeId(e.getId());
//                    node.setNodeName(e.getName());
//                    node.setProcDefId(procDefId);
//                    node.setCreateAt(new Date());
//                    node.setCreateBy("system");
//                    node.setUpdateAt(new Date());
//                    nodeService.insertSelective(node);

                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        log.info(sequenceFlow.getId() + " -条件：" + sequenceFlow.getConditionExpression() + " -SRC:"
                                + sequenceFlow.getSourceRef() + " -DST:" + sequenceFlow.getTargetRef() + "-");

                        String condStr = sequenceFlow.getConditionExpression();
                        String cond;
                        if(condStr == null || "".equals(condStr)){
                            cond = "0";
                        } else {
                            cond = condStr.substring(condStr.indexOf("'")+1,condStr.lastIndexOf("'"));
                        }

//                        WorkFlowRoute route = new WorkFlowRoute();
//                        route.setRouteId(sequenceFlow.getId());
//                        route.setRouteName(sequenceFlow.getName());
//                        route.setSrcNodeId(sequenceFlow.getSourceRef());
//                        route.setDstNodeId(sequenceFlow.getTargetRef());
//                        route.setProcDefId(procDefId);
//                        route.setRouteCond(cond);
//                        route.setCreateBy("system");
//                        route.setCreateAt(new Date());
//
//                        routeService.insertSelective(route);
                    }
                }

                if (e instanceof EndEvent) {
                    EndEvent userTask = (EndEvent) e;

                    log.info("NodeId:" + e.getId() + "  -----Name:" + e.getName());
//                    WorkFlowNode node = new WorkFlowNode();
//                    node.setNodeId(e.getId());
//                    node.setNodeName(e.getName());
//                    node.setProcDefId(procDefId);
//                    node.setCreateAt(new Date());
//                    node.setCreateBy("system");
//                    node.setUpdateAt(new Date());
//                    nodeService.insertSelective(node);

                    //获取入线信息
                    List<SequenceFlow> incomingFlows = userTask.getIncomingFlows();
                    for (SequenceFlow sequenceFlow : incomingFlows) {
                        log.info(sequenceFlow.getId() + " -条件：" + sequenceFlow.getConditionExpression() + " -SRC:"
                                + sequenceFlow.getSourceRef() + " -DST:" + sequenceFlow.getTargetRef() + "-");

                        String condStr = sequenceFlow.getConditionExpression();
                        String cond;
                        if(condStr == null || "".equals(condStr)){
                            cond = "0";
                        } else {
                            cond = condStr.substring(condStr.indexOf("'")+1,condStr.lastIndexOf("'"));
                        }

//                        WorkFlowRoute route = new WorkFlowRoute();
//                        route.setRouteId(sequenceFlow.getId());
//                        route.setRouteName(sequenceFlow.getName());
//                        route.setSrcNodeId(sequenceFlow.getSourceRef());
//                        route.setDstNodeId(sequenceFlow.getTargetRef());
//                        route.setProcDefId(procDefId);
//                        route.setRouteCond(cond);
//                        route.setCreateBy("system");
//                        route.setCreateAt(new Date());
//
//                        routeService.insertSelective(route);

                    }
                }
            }
        }
    }



}
