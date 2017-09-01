package cn.itcast.bos.service;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.PageBean;
/**
 * 定区业务
 * @Path cn.itcast.bos.service.IDecidedzoneService
 * @Description TODO
 * @date 2016年12月13日上午8:58:53
 * @author 刘定新
 * @version：1.0
 */
public interface IDecidedzoneService {

	public void save(Decidedzone model, String subareaid);

	public void pageQuery(PageBean pageBean);

}
