package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport{

	//定义流程引擎
	@Resource
	private ProcessEngine processEngine;
	
	/**
	 * 查询我的组任务
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String findGroupTask(){
		
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		
		User user=	(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		//组任务过滤
		query.taskCandidateUser(user.getId());
	
		List<Task> list = query.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
		return "grouplist";
	}
	/**
	 * 查询业务数据
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	private String taskId;
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String showData() throws IOException{
		Map<String, Object> map = processEngine.getTaskService().getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		
		ServletActionContext.getResponse().getWriter().print(map);
		
		return NONE;
	}
	/**
	 * 查询个人任务
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2017年5月30日   刘定新    新建
	 */
	public String findPersonalTask(){
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		User user=	(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		query.taskAssignee(user.getId());
		
		List<Task> list = query.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
		return "personalTaskList";
	}
	/**
	 * 拾取组任务
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2017年5月31日   刘定新    新建
	 */
	public String takeTask(){
		User user=	(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		processEngine.getTaskService().claim(taskId, user.getId());
		return "toGroupTaskList";
	}
	
}
