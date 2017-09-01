package cn.itcast.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Staff;

public interface IStaffService {

	public void save(Staff model);
	/**
	 * 离线条件查询
	 * @param pageBean
	 */
	public void pageQuery(PageBean pageBean);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Staff findById(String id);
	/**
	 * 更新
	 * @param staff
	 */
	public void update(Staff staff);
	public void delete(String ids);
	/**
	 * 查询所有
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public List<Staff> findAll();
	public List<Staff> findByCritra(DetachedCriteria dc);

}
