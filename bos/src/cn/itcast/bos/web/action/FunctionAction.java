package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{

	public String list(){
		
		List<Function> list= functionService.findAll();
		
		this.list2Json(list, new String[]{"function","functions","roles"});
		
		return NONE;
	}
	/**
	 * 菜单加载
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月18日   刘定新    新建
	 */
	public String findMenu(){
		
		User user=(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		
		List<Function> list=functionService.findMenu(user);
		
		this.list2Json(list, new String[]{"function","functions","roles"});
		
		return NONE;
	}
}
