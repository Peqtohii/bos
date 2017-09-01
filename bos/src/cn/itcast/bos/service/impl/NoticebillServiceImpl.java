package cn.itcast.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.INoticebillDao;
import cn.itcast.bos.dao.IWorkbillDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.domain.Workbill;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.crm.service.CustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{

	@Resource
	private INoticebillDao noticebillDao;
	
	@Resource
	private CustomerService customerService;
	
	@Resource
	protected IDecidedzoneDao decidedzonedao;
	
	@Resource
	protected IWorkbillDao workbilldao;
	/**
	 * 保存方法并尝试产生工单（workbill）
	 * TODO
	 * @param model
	 * @see cn.itcast.bos.service.INoticebillService#save(cn.itcast.bos.domain.Noticebill)
	 */
	@Override
	public void save(Noticebill model) {
		// TODO Auto-generated method stub
		noticebillDao.save(model);//变为持久化对象po，继续对他操作，会自动更新
		/*业务受理单与工单的联系：
		 * 	自动生成工单的原理
		 * 	1.从业务受理单中获取地址，根据取件地址查询用户表的定区id
		 * 	2.通过id查到对应的定区数据，定区里面含有取派员的信息，间接的将取派员和业务受理关联
		 * 	3.然后创建一个工单，工单的数据包含
		 * 业务受理单
		 * 类型type：工单类型：新、追、改、销
		 * pickstate取件状态：未取件、取件中、已取件
		 * buildtime创建时间
		 * attachbilltimes追单次数int
		 * remark备注
		 */
		
		//客户取件地址
		String pickaddress = model.getPickaddress();
		
		//查询定区id
		String dqid = customerService.findDecidedzoneidByAddress(pickaddress);
		
		if(dqid != null){
			//根据定区id查询定区
			Decidedzone decidedzone = decidedzonedao.findById(dqid);
			
			//获取去派遣员
			Staff staff = decidedzone.getStaff();
			
			model.setStaff(staff);
			
			model.setOrdertype("自动");
			
			Workbill workbill=new Workbill();
			
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp( System.currentTimeMillis()));
			workbill.setNoticebill(model);
			workbill.setStaff(staff);
			workbill.setType("新");
			workbill.setRemark(model.getRemark());
			workbill.setPickstate("未取件");
			//保存工单
			workbilldao.save(workbill);
			
		}else{
			//如果没有生成，改为手生成
			model.setOrdertype("手动");
		}
		
	}

}
