package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Resource
	private IRoleDao roleDao;

	@Resource
	private ProcessEngine processEngine;
	/**
	 * 添加角色是，将数据同步到activitie的group表中
	 * TODO
	 * @param role
	 * @param functionIds
	 * @see cn.itcast.bos.service.IRoleService#save(cn.itcast.bos.domain.Role, java.lang.String)
	 */
	@Override
	public void save(Role role, String functionIds) {
		roleDao.save(role);//po
		//关联组
		Group group =new GroupEntity();
		group.setId(role.getName());
		processEngine.getIdentityService().saveGroup(group);
		
		String[] ids = functionIds.split(",");
		
		for (String id : ids) {
			//关联角色和权限的两个表
			Function function =new Function(); 
			function.setId(id);
			role.getFunctions().add(function);
		}
		
		
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
	
	
}
