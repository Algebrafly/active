package com.xiaoyun.active.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaoyun.active.config.ProcessDiagramGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

/**
*@Description: modelCL
 * 用于模型的建立与管理
*@Param:
*@return:
*@Author: firstSeven
*@date: 2019/5/17
*/
@Slf4j
@RestController
@RequestMapping("/model")
@Api(value = "/model", description = "模型的建立与管理")
public class ActivitiModelController {
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    ObjectMapper objectMapper;

    /**
    *@Description: 新建一个空模型
     *新建页面
    *@Param:
     * key 启动流程会用
     * name
     * description
     * revision,
     * category
    *@return: java.lang.String
    *@Author: firstSeven
    *@date: 2019/5/17
    */
    @PostMapping("/create")
    @ApiOperation(value = "新建一个空模型")
    public String newModel(@RequestParam(name = "key") String key,
                           @RequestParam(name = "name",defaultValue = "new-process") String name,
                           @RequestParam(name = "description",required = false,defaultValue = "") String description,
                           @RequestParam(name = "revision",required = false,defaultValue = "1") int revision,
                           @RequestParam(name = "category") String category) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(MODEL_NAME, name);
        modelNode.put(MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setCategory(category);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));

        log.info("创建的流程Id是："+id);
       return  "创建的流程Id是："+id;
    }

   /**
   *@Description: 获取所有模型
   *@Param: []
   *@return: java.lang.Object
   *@Author: firstSeven
   *@date: 2019/5/17
   */
    @GetMapping("/modelList")
    @ApiOperation(value = "获取所有模型")
    public Object modelList(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.createModelQuery().list();
    }

    /**
    *@Description: 发布模型为流程定义
    *@Param: [modelId]
    *@return: java.lang.Object
    *@Author: firstSeven
    *@date: 2019/5/17
    */
    @PostMapping("/{modelId}/deploy")
    @ApiOperation(value = "发布模型为流程定义")
    public Object deploy(@PathVariable String modelId) throws Exception {
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return "数据模型不符要求，请至少设计一条主线流程。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return "部署成功";
    }

    /**
    *@Description: 导出model的xml文件
    *@Param: [request, response, modelId]
    *@return: void
    *@Author: firstSeven
    *@date: 2019/5/17
    */
    @GetMapping(value = "export/{modelId}")
    @ApiOperation(value = "导出model的xml文件")
    public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String modelId) {
        // 清空response
        response.reset();
        // 设置response的Header
        response.setContentType("application/octet-stream");
        String agent =  request.getHeader("USER-AGENT");
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);


            String filename = modelData.getKey() + ".bpmn20.xml";
            // 火狐
            if(agent != null &&  agent.toLowerCase().indexOf("firefox") > 0) {
                String enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else {
                String enableFileName = new String(filename.getBytes("GBK"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            }

            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }

    /**
    *@Description: 获取流程图像，已执行节点和流程线高亮显示
    *@Param: [pProcessInstanceId, response]
    *@return: void
    *@Author: firstSeven
    *@date: 2019/5/17
    */
    @GetMapping(value = "processImage")
    @ApiOperation(value = "获取流程图像，已执行节点和流程线高亮显示")
    public void processImage(@RequestParam(name = "procInstId") String pProcessInstanceId, HttpServletResponse response) throws Exception {
        log.info("[开始]-获取流程图图像");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(pProcessInstanceId).singleResult();

            if (historicProcessInstance == null) {
                throw new Exception("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<>();
                int index = 1;
                log.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    log.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                // 获取流程图图像字符流
                InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            log.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            log.error("【异常】-获取流程图失败！" + e.getMessage());
            throw new Exception("获取流程图失败！" + e.getMessage());
        }
    }

}