package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 工作单管理
 * @Path cn.itcast.bos.web.action.WorkordermanageAction
 * @Description TODO
 * @date 2016年12月15日下午8:02:07
 * @author 刘定新
 * @version：1.0
 */

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	
	public String save() throws IOException{
		String flag="1";
		System.out.println(model.getArrivecity());
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			// TODO: handle exception
			 flag="0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset:utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return NONE;
	}
	
	public String pageQuery(){
		
		//首先封装属性
		workordermanageService.pageQuery(pageBean);
		
		//将pagebean转化为json数据
		this.pageBean2Json(pageBean,new String[]{"vol"});
		
		return NONE;
	}
	/**
	 * 查询状态为未启动的工作单
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String list(){
		List<Workordermanage> list =workordermanageService.findListNotStart();
		
		ActionContext.getContext().getValueStack().set("list", list);
		
		return "list";
	}
	/**
	 * 启动
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月22日   刘定新    新建
	 */
	public String start(){
	
		workordermanageService.start(model.getId());
		return "toList";
	}
	
	
}
