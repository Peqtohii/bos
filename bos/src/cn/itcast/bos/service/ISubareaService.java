package cn.itcast.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Subarea;

/**
 * 分区业务层接口
 * @Path cn.itcast.bos.service.ISubareaService
 * @Description TODO
 * @date 2016年12月12日下午4:45:55
 * @author 刘定新
 * @version：1.0
 */
public interface ISubareaService {
	/**
	 * 保存方法
	 * TODO
	 * @param model<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public void save(Subarea model);

	/**
	 * 分页查询的方法
	 * TODO
	 * @param pageBean<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public void pageQuery(PageBean pageBean);

	/**
	 * 查询所有
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public List<Subarea> findAll();

	public List<Subarea> findBycriteria(DetachedCriteria criteria);

}
