package com.nal.raj.workflow.framework;



import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;

public class DeployWorkflow {
  
  private ProcessHelper helper;

  private List<String> processFiles = new ArrayList<String>();
 
  /**
   * @return the processFiles
   */
  public List<String> getProcessFiles() {
    return processFiles;
  }

  /**
   * @param processFiles
   *          the processFiles to set
   */
  public void setProcessFiles( List<String> processFiles ) {
    this.processFiles = processFiles;
  }

  /**
   * @return the helper
   */
  public ProcessHelper getHelper() {
    return helper;
  }

  /**
   * @param helper
   *          the helper to set
   */
  public void setHelper( ProcessHelper helper ) {
    this.helper = helper;
  }

  public void init() {
    for( String fileName : processFiles ) {
      Deployment deployment = getHelper().getRepositoryService()
        .createDeployment()
        .addClasspathResource( fileName )
        .deploy();
     
    }
   /* List<ProcessDefinition> processDefinitions = getHelper().getRepositoryService()
      .createProcessDefinitionQuery()
      .list();*/
     }
}
