import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nal.raj.workflow.framework.DeployWorkflow;
import com.nal.raj.workflow.framework.ProcessHelper;
import com.nal.raj.workflow.framework.WorkflowManager;

/**
 * A ProcessTest is ...
 * @author dmitry.serdyuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/activiti.cfg.xml"})
public class ProcessTest
{

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Resource
	public RepositoryService repositoryService;
	/**
	 * Inject runtime service
	 */	
	@Resource
	public RuntimeService runtimeService;
	/**
	 * Inject task service
	 */	
	@Resource
	public TaskService taskService;
	
	@Resource
	public ProcessHelper helper;
	
	private DeployWorkflow deployer;

	public ProcessHelper getHelper() {
		return helper;
	}
	public void setHelper(ProcessHelper helper) {
		this.helper = helper;
	}
	public DeployWorkflow getDeployer() {
		return deployer;
	}
	public void setDeployer(DeployWorkflow deployer) {
		this.deployer = deployer;
	}

	@Test
	public void startProcess() throws Exception {
		
		 WorkflowManager workflowManager = new WorkflowManager(getHelper());
		 ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("activiti.cfg.xml");
		 
		 String orderInstanceId = StartOrderFlow("Order");
		 String itemsInstanceID = StartItemsFlow(orderInstanceId,"Items");
		 
		 System.out.println("Oder InProgress call");
		 workflowManager.signalOrderInProgress(orderInstanceId);
		 assertTrue( "InProgress".equals(GetProcessState(orderInstanceId ) ));
		 System.out.println("\n"+"Items InProgress call");
		 workflowManager.signalItemsInProgress(itemsInstanceID);
		 assertTrue( "InProgress".equals(GetProcessState(itemsInstanceID ) ));
		 System.out.println("\n"+"Oder SignOut call");
		 workflowManager.signalOrderSignOut(orderInstanceId);
		 assertTrue( "SignOut".equals(GetProcessState(orderInstanceId ) ));
		 assertTrue( "SignOut".equals(GetProcessState(itemsInstanceID ) ));
		 
	}
	
	private String StartOrderFlow(String key) {
		Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put( "ItemsProcesse", new String());
	    String processInstanceId = startProcess( key, variables );
	    return processInstanceId;
		
	}
	
	private String StartItemsFlow(String orderId, String key) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put( "orderPorcessId", orderId );
		String processInstanceId = startProcess( key, variables );
		setVariableValue(orderId, "ItemsProcesses", processInstanceId);
		return processInstanceId;
		
	}

	private String startProcess(String key, Map<String, Object> variables) {
		ProcessInstance instance = runtimeService.startProcessInstanceByKey( key, variables );
		return instance.getId();
	}

	public void setVariableValue(String processId, String variableName, Object value) {
		runtimeService.setVariable(processId, variableName, value);
	}
	
	public String GetProcessState(String processId) {

		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processId).singleResult();
		Task task = taskService.createTaskQuery().executionId(instance.getId())
				.singleResult();
		return task.getName();
	}
}
