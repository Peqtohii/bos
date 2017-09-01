package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.IDecidedzoneService;

/**
 * 定区服务层
 * @Path cn.itcast.bos.service.impl.DecidedzoneServiceImpl
 * @Description TODO
 * @date 2016年12月12日下午9:49:44
 * @author 刘定新
 * @version：1.0
 */
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{

	@Resource
	private IDecidedzoneDao decidedzoneDao;
	
	//获取分区的dao
	@Resource
	private  ISubareaDao subareaDao;

	/**
	 * 保存方法吗，同时关联定区与分区
	 * TODO
	 * @param model
	 * @see cn.itcast.bos.service.IDecidedzoneService#save(cn.itcast.bos.domain.Decidedzone)
	 */
	@Override
	public void save(Decidedzone model,String subareaid) {

		System.out.println(subareaid);
		
		decidedzoneDao.save(model);//po
		//查处对应的分区对象
		Subarea subarea = subareaDao.findById(subareaid);//po
		//设置关联关系，将定区的id存到分区数据库中
		subarea.setDecidedzone(model);
		
		
	}
	/**
	 * 分页
	 * TODO
	 * @param pageBean
	 * @see cn.itcast.bos.service.IDecidedzoneService#pageQuery(cn.itcast.bos.domain.PageBean)
	 */
	@Override
	public void pageQuery(PageBean pageBean) {

		decidedzoneDao.pageQuery(pageBean);
	}
}
