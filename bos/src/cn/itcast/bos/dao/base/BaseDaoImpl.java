package cn.itcast.bos.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.bos.domain.PageBean;
/**
 *
 * @Path cn.itcast.bos.dao.base.BaseDaoImpl
 * 公用的Dao
 * @date 2016年12月12日下午2:47:10
 * @author 刘定新
 * @version：1.0
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	//使用注解方式提供session工厂
	@Resource
	public void setSF(SessionFactory sessionFactory){
		//调用父类的方法注入回话工厂对象
		super.setSessionFactory(sessionFactory);
	} 
	
	//最大的问题是怎样获取实体类型
	
	//定义一个属性，表示实体的类型
	private Class<T> domainclass;
	
	/**
	 * 在构造方法中动态获得实体的类型
	 */
	public BaseDaoImpl() {
		//获取父类的类型（通过继承该类的的class获取父类类型）
		//得到的是type类型，转为子类ParameterizedType
		ParameterizedType genericSuperclass = 
				(ParameterizedType) this.getClass().getGenericSuperclass();
		//通过父类类型获取父类的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//Type是class的父类
		domainclass=(Class<T>) actualTypeArguments[0];
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}
/**
 * 根据id查询
 */
	public T findById(Serializable id) {
		return 	this.getHibernateTemplate().get(domainclass, id);
	}
	
	public List<T> findAll() {
		String hql="from "+domainclass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

/**
 * 根据条件查询对象查询
 */
	/**
	 * 更容易实现多条件组合查询，通过criteria添加条件 
	 */
	public List<T> findByCriteria(DetachedCriteria criteria) {
		
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
/**
 * 根据命名查询语句查询
 */
	public List<T> findByNamedQuery(String queryName, Object... args) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, args);
	}
	
/**
 * 执行增删改操作的命名语句
 */
	public void executeNamedQuery(String queryName, Object... args) {
		Session session=this.getSession();
		Query query = session.getNamedQuery(queryName);
		if(args != null && args.length>0){
			int i=0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		query.executeUpdate();
		
	}

/**
 * 通用的分页查询
 */
public void pageQuery(PageBean pagebean) {

	int currentPage=pagebean.getCurrentPage();//当前页码
	int pageSize=pagebean.getPageSize();//每页记录数
	DetachedCriteria detachedCriteria = pagebean.getDetachedCriteria();
	
	//查询总记录数指定hibernate框架发出select count(id) from xxx where ...统计数据量的sql
	detachedCriteria.setProjection(Projections.rowCount());
	List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
	Long total=list.get(0);
	
	//查询当前页面的数据集合，首先充值离线查询条件detachedCriteria   指定 hibernate框架发出select * from xxx where....
	detachedCriteria.setProjection(null);
	//指定hibernate框架将从数据表中查询的每行数据对应的包装成一个实体对象
	detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);

	//分页查询开始位置
	int begin=(currentPage-1)*pageSize;
	
	List pagelist=this.getHibernateTemplate().findByCriteria(detachedCriteria, begin, pageSize);
	
	pagebean.setTotal(total.intValue());
	pagebean.setRows(pagelist);
	
	
}

}
