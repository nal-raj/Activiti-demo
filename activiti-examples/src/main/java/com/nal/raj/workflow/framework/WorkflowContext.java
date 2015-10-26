package com.nal.raj.workflow.framework;

public class WorkflowContext {

	StateChangeCallBack stateChangeCallBack;
	
	private static ThreadLocal<WorkflowContext> context = new ThreadLocal<WorkflowContext>();

	  /**
	   * This method sets the current invocation context.
	   * @param wcontext
	   *          - {@link InvocationContext} context.
	   */
	  public static void setContext( WorkflowContext wcontext ) {
	    if ( wcontext == null ) {
	      context.remove();
	    }
	    context.set( wcontext );
	  }

	  /**
	   * This method returns the context in the current thread.
	   * @return
	   */
	  public static WorkflowContext getContext() {
	    return context.get();
	  }

	public StateChangeCallBack getStateChangeCallBack() {
		return stateChangeCallBack;
	}

	public void setStateChangeCallBack(StateChangeCallBack stateChangeCallBack) {
		this.stateChangeCallBack = stateChangeCallBack;
	}

}
