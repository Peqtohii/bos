package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.crm.domain.Customer;
/**
 * 客户服务接口 
 * @Path cn.itcast.crm.service.CustomerService
 * @Description TODO
 * @date 2016年12月13日下午9:28:45
 * @author 刘定新
 * @version：1.0
 */

public interface CustomerService {
	// 未关联定区客户
	public List<Customer> findnoassociationCustomers();

	// 查询已经关联指定定区的客户
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// 将未关联定区客户关联到定区上
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
	
	//根据手机号查询客户信息
	public Customer findCustomerByPhone(String phone);
	
	//根据客户的取件地址查询定区id
	public String findDecidedzoneidByAddress(String address);
}
