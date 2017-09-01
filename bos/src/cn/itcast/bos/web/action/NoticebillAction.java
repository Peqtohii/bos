package cn.itcast.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;

/**
 * 业务受理单
 * @Path cn.itcast.bos.web.action.NoticebillAction
 * @Description TODO
 * @date 2016年12月13日下午8:58:52
 * @author 刘定新
 * @version：1.0
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//提供属性驱动，用于接受参数
	private String phone;
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 根据手机号查询用户
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public String findByPhone(){
		
		Customer customer = customerService.findCustomerByPhone(phone);
		System.out.println(phone);
		this.object2Json(customer, new String[]{"decidedzone_id"});
		
		return NONE;
	}
	/**
	 * 
	 *保存业务受理单，并尝试自动生成工单
	 *：物流公司业务人员将客户的委托信息（业务通知单）录入到系统中后，
	 *bos系统会根据客户的地址自动匹配一个取派员上门取件，为取派员创建一个任务-----工单
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月13日   刘定新    新建
	 */
	public String save(){
		//设置管理员关联
		User user =(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		
		model.setUser(user);
		
		noticebillService.save(model);
		
		return NONE;
	}
}
