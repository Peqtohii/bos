package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.dao.impl.RoleDaoImpl;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.util.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Resource
	private ProcessEngine processEngine;
	
	@Resource
	private IRoleDao roleDao;
	
	@Resource
	private IUserDao userdao;

	@Override
	public User login(User user) {
		
		String password = MD5Utils.md5(user.getPassword());
		
		List<User> list = userdao.findByNamedQuery("findByUsernameAndPassword", user.getUsername(),password);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
		
	}
/**
 * 修改密码
 */
	@Override
	public void editPassword(String id, String password) {
		String password1 =MD5Utils.md5(password);
		
		userdao.executeNamedQuery("editPassword", password1,id);
	}
	/**
	 * 保存方法同时保存角色信息
	 * TODO
	 * @param model
	 * @param roleTD
	 * @see cn.itcast.bos.service.IUserService#save(cn.itcast.bos.domain.User, java.lang.String)
	 */
@Override
public void save(User user, String[] roleIds) {

	//加密密码
	String password = user.getPassword();
	
	 password = MD5Utils.md5(password);
	 
	 user.setPassword(password);
	 
	 userdao.save(user);
	 
	 org.activiti.engine.identity.User actUser = new UserEntity();
	actUser.setId(user.getId());
	processEngine.getIdentityService().saveUser(actUser);
	
	 //关联角色和用户
	if(roleIds != null && roleIds.length >0){
	 for (String id : roleIds) {
	
		 Role role = roleDao.findById(id);
		user.getRoles().add(role);
		//设置群组里面的人员(群组id，人员id)
		processEngine.getIdentityService().createMembership(actUser.getId(), role.getName());
		
	 }
	}
	 
}
	@Override
	public List<User> findAll() {
		return userdao.findAll();
	}
	
}
