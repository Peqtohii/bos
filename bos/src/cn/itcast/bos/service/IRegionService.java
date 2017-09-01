package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Region;

public interface IRegionService {

	/**
	 * 批量保存
	 * @param list
	 */
	public void saveBatch(List<Region> list);

	/**
	 * 分页查询
	 * @param pagebean
	 */
	public void queryPage(PageBean pagebean);

	/**
	 * 查询全部
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public List<Region> findAll();

}
