package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IFunctionService;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{

	@Resource
	public IFunctionDao functionDao;

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}
/**
 * 根据用户权限查询菜单数据
 * TODO
 * @param user
 * @return
 * @see cn.itcast.bos.service.IFunctionService#findMenu(cn.itcast.bos.domain.User)
 */
	@Override
	public List<Function> findMenu(User user) {
		 List<Function> list= null;
		//超级管理员查询所有
		 if(user.getUsername().equals("admin")){
			 
			list= functionDao.finfAllMenu();
			 
		 }else{
			 list =functionDao.findMenu(user.getId());
		 }
		 
		 
		return list ;
	}
}
