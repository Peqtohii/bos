package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.util.MD5Utils;
import cn.itcast.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{

	//角色id
	private String[] roleIds;
	
	
	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	//验证码
	private String checkcode;
	
	
	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}


/**
 *框架验证登录
 * TODO
 * @return<br/>
 * ============History===========<br/>
 * 2016年12月16日   刘定新    新建
 */
	public String login(){
		//从session中获取验证码
		String value=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		 
		if(StringUtils.isBlank(checkcode) || !checkcode.equals(value)){
			//验证失败
			//从properties中获取
			this.addActionError(this.getText("CheckError"));
			return "login";
		}else{
			//验证成功
			//使用shiro提供的方式进行权限验证
			//获取当前用户对象,状态为"未认证"
			Subject subject = SecurityUtils.getSubject();
			String username =model.getUsername();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			//new出一个token对象
			AuthenticationToken token=new UsernamePasswordToken(username, password);
			try {
				
				//调用安全管理器(已配置),安全管理器再调用realm(BosReaml)
				subject.login(token);
				System.out.println("--------------------------");
				User user=(User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("loginuser", user);
				
			} catch (UnknownAccountException e) {
				// TODO: handle exception
				this.addActionError("用户名不存在");
				return "login";
			}catch (IncorrectCredentialsException e) {
				// TODO: handle exception
				this.addActionError("用户名或密码错误");
				return "login";
			}
		}
			return "home";
	}
/**
 * 登录	
 * @return
 */
	public String login_bak(){
		//从session中获取验证码
		String value=(String) ServletActionContext.getRequest().getSession().getAttribute("key");
		 
		if(StringUtils.isBlank(checkcode) || !checkcode.equals(value)){
			//验证失败
			//从properties中获取
			this.addActionError(this.getText("CheckError"));
			return "login";
		}else{
			//验证成功
		User user=	userservice.login(model);
		if(user!=null){
			ServletActionContext.getRequest().getSession().setAttribute("loginuser", user);
			return "home";
		}else{
			this.addActionError(this.getText("LoginError"));
			return "login"; 
		}
			
		}
	}
	/**
	 * 用户退出
	 */
	public String logout(){
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	/**
	 * 修改密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		
		//获取新的密码
		String password = model.getPassword();
		
		//从session中获取当前用户id
		User user=(User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		
		String id=user.getId();
		
		String f="1";
		//使用查询语句修改密码
		try {
			userservice.editPassword(id,password);
		} catch (Exception e) {
			e.printStackTrace();
			f="0";
			// TODO: handle exception
		}
		System.out.println(f);
		ServletActionContext.getResponse().setContentType("text/html;charaset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
	/**
	 * 添加用户的方法
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月18日   刘定新    新建
	 */
	public String save(){
		
		userservice.save(model,roleIds);
		
		return "list";
	}
	
	public String list(){
		
		List<User> list=userservice.findAll();
		
		this.list2Json(list, new String[]{"noticebills","roles"});
		
		return NONE;
	}
	
}
