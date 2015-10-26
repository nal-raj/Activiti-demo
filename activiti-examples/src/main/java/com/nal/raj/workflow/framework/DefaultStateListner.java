package com.nal.raj.workflow.framework;


import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;

public class DefaultStateListner implements TaskListener {

	public void notify(DelegateTask arg0){

		WorkflowContext context = WorkflowContext.getContext();
		StateChangeCallBack callback = context.getStateChangeCallBack();
		String instanceId = arg0.getProcessInstanceId();
		if (callback != null) {
			callback.stateChanged(instanceId);
		}

	}
}
