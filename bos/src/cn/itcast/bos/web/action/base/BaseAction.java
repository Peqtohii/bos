package cn.itcast.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.service.IFunctionService;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.crm.service.CustomerService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected PageBean pageBean=new PageBean();
	
	protected T model;
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	//通用的分页查询

/*代码重构
 * 	protected int page1;
	protected int rows1;
	this.page1=page
	this.rows1=rows
	pageBean.setCurrentPage(page1);
	pageBean.setPageSize(rows1);
 * 上边的两个字段是为了获取页数和每页显示数，以便于向pagebean封装数据，可以直接使用set方法项pagebean封装
 * 无需额外定义这两个字段
 * 
 */
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
		//业务层
		@Resource
		protected IUserService userservice;

		@Resource
		protected IStaffService staffService;
		
		@Resource
		protected IRegionService regionService;
	
		@Resource
		protected ISubareaService subareaService;
		
		@Resource
		protected IDecidedzoneService decidedzoneservice;
		
		@Resource
		protected CustomerService customerService;
		
		@Resource
		protected INoticebillService noticebillService;
		
		@Resource
		protected IWorkordermanageService workordermanageService;
		
		@Resource
		protected IFunctionService functionService;
		
		@Resource
		protected IRoleService roleService;
		
		//编写公用的离线查询条件
		protected DetachedCriteria detachedCriteria;
		
		
	//在构造方法中获取父类的类型，通过反射获取实体类对象
	public BaseAction() {
		//this.就是继承它的类
		ParameterizedType type = null;
		Type generic =this.getClass().getGenericSuperclass();
			if(generic instanceof ParameterizedType){
					
				type=(ParameterizedType) generic;
			}else{
				
				//因为使用了cglib的动态代理,这里的父类实际上是继承原来类的代理类,所以要找到原来类的类型,需要继续向上寻找一层
				type=(ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
				
			}
				
		Type[] types = type.getActualTypeArguments();
		
		Class<T> domainclass= (Class<T>) types[0];
		//封装查询条件
		detachedCriteria=DetachedCriteria.forClass(domainclass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		try {
			//通过反射实例化对象
			model=	domainclass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将pagebean转化为json的公用方法
	 * 
	 */
	public void pageBean2Json(PageBean pageBean,String[] string){
		//限制条件
		JsonConfig config=new JsonConfig();
		config.setExcludes(string);
		//将数据转化为json
		//page 里面有该页的数据集合
		JSONObject jsonObject=JSONObject.fromObject(pageBean,config);
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * list集合转化为json 的方法
	 * TODO
	 * @param list
	 * @param s<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public void list2Json(List list ,String[] s){
		JsonConfig config =new JsonConfig();
		config.setExcludes(s);
		
		JSONArray jsonArray = JSONArray.fromObject(list,config);
		String json = jsonArray.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void object2Json(Object ob,String[] string){
		//限制条件
		JsonConfig config=new JsonConfig();
		config.setExcludes(string);
		//将数据转化为json
		//page 里面有该页的数据集合
		JSONObject jsonObject=JSONObject.fromObject(ob,config);
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
