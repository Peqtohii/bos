package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	/**
	 * 根据用户id查询用户
	 * TODO
	 * @param id
	 * @return
	 * @see cn.itcast.bos.dao.IFunctionDao#findFunction(java.lang.String)
	 */
	@Override
	public List<Function> findFunction(String id) {
		
		String hql= "from Function f left outer join fetch " +
				"f.roles r left outer join fetch r.users u where u.id = ?";
		
		return this.getHibernateTemplate().find(hql,id);
	}
/**
 * 查询菜单
 * TODO
 * @return
 * @see cn.itcast.bos.dao.IFunctionDao#finfAllMenu()
 */
	@Override
	public List<Function> finfAllMenu() {

		String hql="from Function f where f.generatemenu ='1' order by f.zindex  ";
		
		return this.getHibernateTemplate().find(hql);
	}
@Override
public List<Function> findMenu(String id) {

	String hql="select distinct f from Function f left outer join fetch "
			+ "f.roles r left outer join fetch "
			+ "r.users u where f.generatemenu ='1' and u.id = ? order by f.zindex ";
	
	return this.getHibernateTemplate().find(hql,id);
}

}
