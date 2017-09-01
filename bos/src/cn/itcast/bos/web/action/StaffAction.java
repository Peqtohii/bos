package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.web.action.base.BaseAction;
/**
 * 取派员action
 * @author Subway
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{

	
	private String ids;
	
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
/**
 * 保存添加
 * @return
 */
	public String save(){
		System.out.println(model.getName());
		staffService.save(model);
		
		return "list";
	}
/**
	* 分页查询
	* @throws IOException 
	* 
 */
	public String pageQuery() throws IOException{
		
		staffService.pageQuery(pageBean);
		
		this.pageBean2Json(pageBean, new String[]{"decidedzones","detachedCriteria","pageSize","currentPage"});
		
		return NONE;
	}
/**
 * 更新修改方法
 */
	public String update(){
		//先根据id查出来需要修改的用户，然后覆盖
		Staff staff=staffService.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		if(model.getHaspda() !=null){
			staff.setHaspda(model.getHaspda());
		}else{
			staff.setHaspda("0");
		}
		staff.setStandard(model.getStandard());
		staffService.update(staff);
		
		return "list";
	}
	
	/**
	 * 作废方法
	 */

	public String delete(){
	
		staffService.delete(ids);
		
		return "list";
	} 
	
	/**
	 * 向定区界面的下拉列表中提供json数据
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public String findByStaffByAjax(){
		//不能查询所有，因为有停职的员工
		//List<Staff> list=staffService.findAll();
		DetachedCriteria dc=DetachedCriteria.forClass(Staff.class);
		
		dc.add(Restrictions.eq("deltag", "0"));
		
		//再添加一个过滤条件，没有负责定区的员工
		dc.add(Restrictions.isEmpty("decidedzones"));
		
		List<Staff> list=staffService.findByCritra(dc);
				
		String[] s=new String[]{"decidedzones","standard","station","deltag","haspda","telephone"};
	
		this.list2Json(list, s);
		
		return NONE;
	}
}
