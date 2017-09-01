package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;

/**
 * 权限管理service
 * @Path cn.itcast.bos.service.IFunctionService
 * @Description TODO
 * @date 2016年12月18日上午11:05:36
 * @author 刘定新
 * @version：1.0
 */
public interface IFunctionService {

	/**
	 * 查询所有权限
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月18日   刘定新    新建
	 */
	public List<Function> findAll();

	public List<Function> findMenu(User user);
	
}
