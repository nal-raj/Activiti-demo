package com.nal.raj.workflow.framework;

import java.io.Serializable;

import org.activiti.engine.runtime.ProcessInstance;

public interface StateChangeCallBack extends Serializable {

	public void stateChanged( String processInstanceID );
}
