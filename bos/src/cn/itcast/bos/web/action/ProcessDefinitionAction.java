package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义action
 * @Path cn.itcast.bos.web.action.ProcessDefinitionAction
 * @Description TODO
 * @date 2016年12月22日下午3:48:24
 * @author 刘定新
 * @version：1.0
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport{

	//接受上传对象
	private File deploy;
	
	//流程定义id
	private String id;
	
	//注入流程对象
	@Resource
	private ProcessEngine processEngine;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getDeploy() {
		return deploy;
	}

	public void setDeploy(File deploy) {
		this.deploy = deploy;
	}

	/**
	 * 查询流程定义
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String list(){
		//流程查询对象
		ProcessDefinitionQuery query = processEngine
				.getRepositoryService().createProcessDefinitionQuery();
		//查询最新版本
		query.latestVersion();
		
		List<ProcessDefinition> list = query.list();
		
		ActionContext.getContext().getValueStack().set("list", list);
		
		return "list";
	}
	
	//上传(部署流程定义，使用zip压缩包形式)
	public String deploy() throws Exception{
		
		DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
		
		InputStream in =new FileInputStream(deploy);
		builder.addZipInputStream(new ZipInputStream(in ));
		builder.deploy();
		
		return "toList";
	}
	/**
	 *在网页上查看图片
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String viewpng(){
		//根据流程id获取png图片输入流，使用文件下载方式展示到浏览器中
		InputStream pngStream = processEngine.getRepositoryService().getProcessDiagram(id);
		//压到栈顶
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		
		return "viewpng";
	}
}
