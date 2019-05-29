package com.xiaoyun.active.controller;


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: [Activiti工作流公共方法Controller，提供工作流相关公共方法]
 * @Author: firstSeven
 * @CreateDate: [2019-05-14]
 * @Version: [v2.0.0]
 */
@RestController
@RequestMapping("/activitiController")
public class ActivitiController{

	static final Logger logger = Logger.getLogger(ActivitiController.class);

	@Resource
	private RepositoryService repositoryService;

	//active 查询历史任务的service直接调用不再赘述
	@Resource
	private HistoryService historyService;



}
