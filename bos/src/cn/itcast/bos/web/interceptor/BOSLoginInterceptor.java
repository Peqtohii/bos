package cn.itcast.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 拦截器
 * @author Subway
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{

	
	protected String doIntercept(ActionInvocation in) throws Exception {
		
		//从session中获取用户
		User user=	(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
	
		if(user == null){
			//未登录转到登录页面
			return "login";
		}else{
			//已登录，放行
			return in.invoke();
		}
	
	}
}
