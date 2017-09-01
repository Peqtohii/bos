package cn.itcast.bos.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Function;
/**
 * 权限管理dao
 * @Path cn.itcast.bos.dao.IFunctionDao
 * @Description TODO
 * @date 2016年12月18日上午11:09:22
 * @author 刘定新
 * @version：1.0
 */

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findFunction(String id);

	public List<Function> finfAllMenu();

	public List<Function> findMenu(String string);


}
