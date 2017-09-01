package cn.itcast.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageService implements IWorkordermanageService{

	//流程引擎对象
	@Resource
	private ProcessEngine engine;
	
	
	@Resource
	private IWorkordermanageDao workordermanageDao;

	@Override
	public void save(Workordermanage model) {
		// TODO Auto-generated method stub
		workordermanageDao.save(model);
	}

	/**
	 * 分页查询的方法
	 * TODO
	 * @param pageBean
	 * @see cn.itcast.bos.service.IWorkordermanageService#pageQuery(cn.itcast.bos.domain.PageBean)
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		workordermanageDao.pageQuery(pageBean);
	}
/**
 * 查询未开启的工作单
 * TODO
 * @return
 * @see cn.itcast.bos.service.IWorkordermanageService#findListNotStart()
 */
	@Override
	public List<Workordermanage> findListNotStart() {

		DetachedCriteria criteria = DetachedCriteria.forClass(Workordermanage.class);
		criteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCriteria(criteria);
	}
/**
 * 启动流程
 * TODO
 * @param id
 * @see cn.itcast.bos.service.IWorkordermanageService#start(java.lang.String)
 */
@Override
public void start(String id) {
	//先查询的工作单对象
	Workordermanage workordermanage = workordermanageDao.findById(id);
	//把订单状态改为1
	workordermanage.setStart("1");
	//设置工作单对象到流程变量表
	String processDefinitionKey = "transfer";
	Map<String, Object> variables = new HashMap<String, Object>();
	variables.put("业务数据", workordermanage);
	engine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey , variables);
}
}
