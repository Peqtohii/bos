package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//分区的id用于保存分区中的定区id'
	private String subareaid;
	
	private Integer[] customerIds;
	

	public Integer[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	public void setSubareaid(String subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 * 
	 * 保存方法同时需要在分区表中保存定区的id
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public String save(){
		
		decidedzoneservice.save(model,subareaid);
		
		return "list";
	}
	/**
	 * 分页查询
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public String pageQuery(){
		
		//先封装pagebean的数据
		decidedzoneservice.pageQuery(pageBean);
		
		//将数据转化为json
		this.pageBean2Json(pageBean, new String[]{
				"detachedCriteria","pageSize","currentPage","subareas","decidedzones"
		});
		return NONE;
	}
	
	/**
	 * 获取未关联定区的客户
	 */

	public String findNoCustomer(){
		//远程调用
		List<Customer> list = customerService.findnoassociationCustomers();
		
		String[] config=new String[]{"station","telephone","address","decidedzone_id"};
		
		this.list2Json(list, config);
		
		return NONE;
	}
	/**
	 * 获取指定定区的客户
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public String findCustomer(){
		//获取指定定区的客户
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		
		String[] config=new String[]{"station","telephone","address","decidedzone_id"};
		
		this.list2Json(list, config);
		
		return NONE;
	}
	/**
	 * 关联客户与定区
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public String assigncustomerstodecidedzone(){
		
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		
		return "list";
	}
}
