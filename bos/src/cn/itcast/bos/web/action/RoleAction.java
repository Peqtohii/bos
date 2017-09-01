package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Role;
import cn.itcast.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//权限的集合字符串
	private String functionIds;

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	/**
	 * 添加用户，并关联权限数据
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月18日   刘定新    新建
	 */
	public String save(){
		
		roleService.save(model,functionIds);
		
		return "list";
	}
	/**
	 * 查询所有
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月18日   刘定新    新建
	 */
	public String list(){
		
		List<Role> list =roleService.findAll();
			
		this.list2Json(list, new String[]{"functions","users"});
			
		return NONE;
	}

}
