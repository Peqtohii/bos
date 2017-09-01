package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
/**
 * 取派员的服务
 * @author Subway
 *
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Resource
	private IStaffDao staffDao;
	/**
	 * 添加取派员
	 */
	@Override
	public void save(Staff model) {
			model.setDeltag("0");
			staffDao.save(model);
		}
	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	@Override
	public Staff findById(String id) {
		
		return staffDao.findById(id);
	}
	@Override
	public void update(Staff staff) {
		staffDao.update(staff);
		
	}
	/**
	 * 作废取派员
	 */
	@Override
	public void delete(String ids) {

		String[] split = ids.split(",");
		
		 for (String id : split) {
			
			Staff staff = staffDao.findById(id); //po对象
			//查询之后放到一级缓存中
			staff.setDeltag("1");			
			/**
			 * 自动执行以下代码，设置之后存放到一级缓存中，之后快照区对比数据，发现不同，自动发送更新语句，更新staff
			 * Hibernate: update bos.bc_staff  set name=?,telephone=?,
			 *  haspda=?,
        		deltag=?,
        		station=?,
        		standard=? 
    				where
        			id=?
			 */
		}
	}
	@Override
	public List<Staff> findAll() {
		
		return staffDao.findAll();
	}
	@Override
	public List<Staff> findByCritra(DetachedCriteria dc) {
		// TODO Auto-generated method stub
		return staffDao.findByCriteria(dc);
	}
}
