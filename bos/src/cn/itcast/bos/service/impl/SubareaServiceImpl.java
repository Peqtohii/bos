package cn.itcast.bos.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;

/**
 * 分区业务层实现类
 * @Path cn.itcast.bos.service.impl.SubareaService
 * @Description TODO
 * @date 2016年12月12日下午4:46:21
 * @author 刘定新
 * @version：1.0
 */
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Resource
	private ISubareaDao subareaDao;

	/**
	 * 保存
	 * TODO
	 * @param model
	 * @see cn.itcast.bos.service.ISubareaService#save(cn.itcast.bos.domain.Subarea)
	 */
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}
	/**
	 * 分页查询的方法
	 * TODO
	 * @param pageBean
	 * @see cn.itcast.bos.service.ISubareaService#pageQuery(cn.itcast.bos.domain.PageBean)
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
		
	}
	/**
	 * 查询所有
	 * TODO
	 * @return
	 * @see cn.itcast.bos.service.ISubareaService#findAll()
	 */
	@Override
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaDao.findAll();
	}
	@Override
	public List<Subarea> findBycriteria(DetachedCriteria criteria) {
		return subareaDao.findByCriteria(criteria);
	}

}
