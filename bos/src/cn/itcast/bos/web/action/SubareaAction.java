package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.util.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;
/**
 * 
 * @Path cn.itcast.bos.web.action.SubareaAction
	分区action
 * @date 2016年12月12日下午4:39:35
 * @author 刘定新
 * @version：1.0
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	/**
	 * 保存方法
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public String save(){
		
		subareaService.save(model);
		
		return "list";
	}
	//分页查询
	public String pageQuery(){
		
		//将数据从实体类中取出来，添加到查询条件中
		
		String addresskey = model.getAddresskey();
		
		Region region = model.getRegion();
		
		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		
		if(StringUtils.isNotBlank(addresskey)){
			//添加查询条件按照地址关键字查询
			criteria.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		//省市条件模糊查询
		if( region != null ){
			/**
			 * ？？？？？？？？？
			 */
			DetachedCriteria criteria1 = criteria.createCriteria("region");
			
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
		
			if(StringUtils.isNotBlank(province)){
				criteria1.add(Restrictions.like("province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				criteria1.add(Restrictions.like("city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				criteria1.add(Restrictions.like("district", "%"+district+"%"));
			}
			
		}
		//封装属性
		subareaService.pageQuery(pageBean);
		
		this.pageBean2Json(pageBean, new String[]{"decidedzone","detachedCriteria","pageSize","currentPage","subareas"});
	
		return NONE;
	}
	
	/**
	 * 下载功能
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		//数据是从后台获取
		List<Subarea> list=subareaService.findAll();
		// 将list集合中的数据写到一个Excel文件中
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件，当前这个文件在内存中
		HSSFSheet sheet = workbook.createSheet("分区数据");// 创建一个sheet页
		HSSFRow headRow = sheet.createRow(0);// 创建标题行
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编码");
		headRow.createCell(2).setCellValue("关键字");
		headRow.createCell(3).setCellValue("起始号");
		headRow.createCell(4).setCellValue("结束号");
		headRow.createCell(5).setCellValue("当双号");
		headRow.createCell(6).setCellValue("位置信息");
		for (Subarea subarea : list) {// 循环list，将数据写到Excel文件中
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			dataRow.createCell(3).setCellValue(subarea.getStartnum());
			dataRow.createCell(4).setCellValue(subarea.getEndnum());
			dataRow.createCell(5).setCellValue(subarea.getSingle());
			dataRow.createCell(6).setCellValue(subarea.getPosition());
		}

		// 文件下载：一个流（输出流）、两个头
		ServletOutputStream out = ServletActionContext.getResponse()
				.getOutputStream();

		String filename = "分区数据.xls";
		filename = FileUtils.encodeDownloadFilename(filename,
				ServletActionContext.getRequest().getHeader("user-agent"));
		ServletActionContext.getResponse().setContentType(
				ServletActionContext.getServletContext().getMimeType(filename));

		ServletActionContext.getResponse().setHeader("content-disposition",
				"attachment;filename=" + filename);
		workbook.write(out);
		
		return NONE;
	}
	/**
	 *为定区提供json数据
	 * TODO
	 * @return<br/>\
	 * 
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public String findByAjax(){
		//添加查询条件，查询那些没有分配定区的分区
		//一个定区可以有多个分区
		DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
		
		criteria.add(Restrictions.isNull("decidedzone"));
		
		//查询
		List<Subarea> list=subareaService.findBycriteria(criteria);
		
		String[] config=new String[]{"decidedzone","region","startnum","endnum","single"};
		
		this.list2Json(list, config);
		//剔除查询
		
		return NONE;
	}

}
