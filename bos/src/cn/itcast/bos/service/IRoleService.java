package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Role;

/**
 *角色service
 * @Path cn.itcast.bos.service.IRoleService
 * @Description TODO
 * @date 2016年12月18日上午11:45:07
 * @author 刘定新
 * @version：1.0
 */
public interface IRoleService {

	public void save(Role model, String functionIds);

	public List<Role> findAll();

}
