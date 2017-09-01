package cn.itcast.bos.Realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
/**
 * shiro框架的权限控制
 * @Path cn.itcast.bos.Realm.BosRealm
 * @Description TODO
 * @date 2016年12月16日下午4:16:55
 * @author 刘定新
 * @version：1.0
 */
public class BosRealm extends AuthorizingRealm{

	@Resource
	private IFunctionDao functionDao;
	
	@Resource
	private IUserDao userdao;
	
	/**
	 * 认证方法
	 * TODO
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken myToken=(UsernamePasswordToken) token;
		String username = myToken.getUsername();
		char[] password = myToken.getPassword();
		//根据用户名查询密码
		User user=userdao.findbyusername(username);
		if(user==null){
			return null;
		}
		//将密码交给安全管理器,自动比对是否登录成功
		//参数一:签名对象
		//参数二:数据库中查出来的密码\
		//参数三:当前realm的类名
		
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
		
		
		
		// TODO Auto-generated method stub
		return info;
	}
	/**
	 * 授权方法
	 * TODO
	 * @param principals
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		
		SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
		//获取当前用户
		User user=(User) principals.getPrimaryPrincipal();
		//授权
		info.addStringPermission("staff");
		List<Function> list =null;
		//根据用户id查询对应权限
		//root用户获得所有权限
		if(user.getUsername().equals("admin")){
			 list = functionDao.findAll();
			
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}else{
			//根据用户id查询权限
			list=functionDao.findFunction(user.getId());
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}
		
		return info;
	}


}
