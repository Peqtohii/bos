package cn.itcast.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport{

	//流程id
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Resource
	private ProcessEngine processEngine;
	
	/**
	 * 查询正在运行的流程
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String list(){
		ProcessInstanceQuery query = processEngine.getRuntimeService().createProcessInstanceQuery();
		
		List<ProcessInstance> list = query.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
			
		return "list";	
	}
	/**
	 * 很具id查询流程变量信息
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 * @throws IOException 
	 */
	public String findData() throws IOException{
		
		Map<String, Object> map = processEngine.getRuntimeService().getVariables(id);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(map);;
		return NONE;
	}
	/**
	 * 展示图片所需的坐标，id png图片名称
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String showPng(){
		
		// 根据流程实例ID查询流程实例对象
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.createProcessInstanceQuery().processInstanceId(id)
				.singleResult();
		// 根据流程实例对象查询对应的流程定义ID
		String processDefinitionId = processInstance.getProcessDefinitionId();
		// 根据流程定义ID查询流程定义对象
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) processEngine
				.getRepositoryService().getProcessDefinition(
						processDefinitionId);
		// 根据流程定义对象获取部署ID和png图片名称
		String deploymentId = processDefinition.getDeploymentId();
		String diagramResourceName = processDefinition.getDiagramResourceName();
		// 根据流程实例对象获取当前流程实例运行到哪个任务节点
		String activityId = processInstance.getActivityId();
		ActivityImpl activityImpl = processDefinition.findActivity(activityId);
		// 获取坐标
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int width = activityImpl.getWidth();
		int height = activityImpl.getHeight();
		ActionContext.getContext().getValueStack()
				.set("deploymentId", deploymentId);
		ActionContext.getContext().getValueStack()
				.set("imageName", diagramResourceName);
		ActionContext.getContext().getValueStack().set("x", x);
		ActionContext.getContext().getValueStack().set("y", y);
		ActionContext.getContext().getValueStack().set("width", width);
		ActionContext.getContext().getValueStack().set("height", height);
		return "showPng";
	}
	private String deploymentId;
	private String imageName;

	// 获取输入流
	public String viewImage() {
		InputStream pngStream = processEngine.getRepositoryService()
				.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "viewImage";
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
