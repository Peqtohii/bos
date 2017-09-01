package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;
/**
 * 区域服务
 * @author Subway
 *服务层需要加事务注释
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{

	@Resource
	private IRegionDao regionDao;

	/**
	 * 批量保存数据
	 */
	@Override
	public void saveBatch(List<Region> list) {
	
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}
	/**
	 * 分页查询
	 */
	@Override
	public void queryPage(PageBean pagebean) {
		regionDao.pageQuery(pagebean);
	}
	/**
	 * 查询所有
	 * TODO
	 * @return
	 * @see cn.itcast.bos.service.IRegionService#findAll()
	 */
	public List<Region> findAll() {
		
		return regionDao.findAll();
	}
	
}
