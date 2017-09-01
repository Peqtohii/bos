package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.PageBean;
import cn.itcast.bos.domain.Workordermanage;

/**
 *工作单管理service接口
 * @Path cn.itcast.bos.service.IWorkordermanageService
 * @Description TODO
 * @date 2016年12月15日下午8:04:26
 * @author 刘定新
 * @version：1.0
 */
public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public void pageQuery(PageBean pageBean);

	public List<Workordermanage> findListNotStart();

	public void start(String id);

}
