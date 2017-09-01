package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	@Override
	public void saveOrUpdate(Region region) {

		this.getHibernateTemplate().saveOrUpdate(region);
	}

	
}
