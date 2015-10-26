package com.nal.raj.workflow.framework;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.springframework.test.context.ContextConfiguration;

public class ProcessHelper {

	private RuntimeService runtimeService;
	private RepositoryService repositoryService;
	private TaskService taskService;
	private HistoryService historyService;
	private ProcessEngine processEngine;

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void signal(String ID) {
		
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(ID).singleResult();
		Task task = taskService.createTaskQuery().executionId(instance.getId())
				.singleResult();
		taskService.complete(task.getId());
	}

	public String GetItem(String orderInstanceId) {
		String itemProcess = (String) runtimeService.getVariable(orderInstanceId, "ItemsProcesses");
		return itemProcess;

	}
	public String GetProcessState(String processId) {

		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processId).singleResult();
		Task task = taskService.createTaskQuery().executionId(instance.getId())
				.singleResult();
		return task.getName();
	}
}
