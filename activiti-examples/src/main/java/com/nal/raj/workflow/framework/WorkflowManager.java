package com.nal.raj.workflow.framework;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.test.context.ContextConfiguration;

public class WorkflowManager {

	private ProcessHelper processHelper;

	public WorkflowManager(ProcessHelper processHelper) {
		super();
		this.processHelper = processHelper;
	}

	public void setProcessHelper(ProcessHelper processHelper) {
		this.processHelper = processHelper;
	}

	public ProcessHelper getProcessHelper() {
		return processHelper;
	}

	public void signalOrderInProgress(String orderInstanceId) {
		System.out.println("Order :: Before State Changed = "+ getProcessHelper().GetProcessState(orderInstanceId) );
		
		InProgressOrder(orderInstanceId);
		//Other Business logic
		System.out.println("Order :: After State Changed = "+ getProcessHelper().GetProcessState(orderInstanceId) );
	}

	private void InProgressOrder(String orderInstanceId) {
		getProcessHelper().signal(orderInstanceId);
	}

	public void signalOrderSignOut(String orderInstanceId) {
		System.out.println("Order :: Before State Changed InstanceiD = "+ getProcessHelper().GetProcessState(orderInstanceId) );
		
		InSignOutOrder(orderInstanceId);
		//Other Business logic
		System.out.println("Order :: After State Changed InstanceiD = "+ getProcessHelper().GetProcessState(orderInstanceId) );
	}

	private void InSignOutOrder(String orderInstanceId) {
		// TODO Auto-generated method stub
		WorkflowContext.setContext(new WorkflowContext());
		WorkflowContext.getContext().setStateChangeCallBack(new StateChangeCallBack() {

		@Override
		public void stateChanged(String orderID) {
			System.out.println("In Sign-Out Order CallBack");
			String itemID = getProcessHelper().GetItem(orderID);
			signalItemsSignOut(itemID);
		}
	});
		getProcessHelper().signal(orderInstanceId);
		
	}

	public void signalItemsInProgress(String itemsInstanceID) {	
		System.out.println("Item :: Before State Changed = "+ getProcessHelper().GetProcessState(itemsInstanceID) );
		
		InProgressItems(itemsInstanceID);
		//Other Business logic
		System.out.println("Item :: After State Changed = "+ getProcessHelper().GetProcessState(itemsInstanceID) );
	}

	private void InProgressItems(String itemsInstanceID) {
		getProcessHelper().signal(itemsInstanceID);
		
	}

	private void signalItemsSignOut(String itemsInstanceID) {		
		System.out.println("Call Sign-Out Item (via- CallBack)");
		System.out.println("Item :: Before State Changed = "+ getProcessHelper().GetProcessState(itemsInstanceID) );
		
		signOutItems(itemsInstanceID);
		//Other Business logic
		System.out.println("Item :: After State Changed = "+ getProcessHelper().GetProcessState(itemsInstanceID) +" -> Expecting (SignOut) with Activiti-5.18.0 like Activiti-5.9 " );
		
	}

	private void signOutItems(String itemsInstanceID) {
		getProcessHelper().signal(itemsInstanceID);
		
	}
	
	
}
