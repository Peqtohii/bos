package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.util.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;
/**
 * 区域管理action
 * @author Subway
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{

	private File myFile;
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	/**
	 * 读取xls中的数据 使用poi工具
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException{
		//加载要读取的文件
		HSSFWorkbook workbook =new HSSFWorkbook(new FileInputStream(
			new File("D:\\区域导入测试数据.xls")));
		//回显数据，判断是否成功
		String flag="1";
		
		//创建一个数组，以便于批量保存 数据
		List<Region> list =new ArrayList<Region>();
		try {
	
		//读取第一个sheet页
		HSSFSheet sheet = workbook.getSheetAt(0);
		//获取当前sheet中的每一行
		for (Row row : sheet) {
			if(row.getRowNum()==0){
				continue;
			}
			//获取当前行的一列
			String id=row.getCell(0).getStringCellValue();
			String province=row.getCell(1).getStringCellValue();
			String city=row.getCell(2).getStringCellValue();
			String district=row.getCell(3).getStringCellValue();
			String postcode=row.getCell(4).getStringCellValue();
			//使用构造方法封装数据
			Region region =new Region(id, province, city, district, postcode);
			
			// 使用Pinyin4J工具生成简码和城市编码
			//简码---北京市北京市东城区=》BJSBJSDCQ
			String shortcode=province + city +district;
			String[] headByString = PinYin4jUtils.getHeadByString(shortcode);
			shortcode= StringUtils.join(headByString, "");
			
			//城市编码
			String[] strings = PinYin4jUtils.stringToPinyin(city);
			String citycode = StringUtils.join(strings);
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			list.add(region);
		}
		//批量保存数据
		regionService.saveBatch(list);
		
		} catch (Exception e) {
			e.printStackTrace();
			flag="0";
		}
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	/**
	 * 分页查询
	 * @return
	 * @throws IOException 
	 */
	public String queryPage() throws IOException{	
		//DetachedCriteria、setCurrentPage、setPageSize属性在父类中已经封装好了
		//调用方法来封装pagebean的其他属性
		regionService.queryPage(pageBean);
		
		//然后调用父类的公用方法把pagebean对象转化为json数据，并响应到jsp页面
		this.pageBean2Json(pageBean, new String[]{"detachedCriteria",
				"pageSize", "currentPage", "subareas" });
		return NONE;
	}
	/**
	 * 查询列表数据，返回到添加分区的下拉列表中
	 * TODO
	 * @return<br/>
	 * ============History===========<br/>
	 * 2016年12月12日   刘定新    新建
	 */
	public String  findListByAjax(){
		//查询所有
		List<Region> list=regionService.findAll();
		//使用json转化tojson，写到下拉列表中，并剔除多余字段
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"subareas"});
		//将list数据转化为json 返回的json中因为在实体类（Region）中添加了getName方法所以页面可以通过name字段获取
		JSONArray jsonObject=JSONArray.fromObject(list,config);
		String json = jsonObject.toString();
				
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return NONE;
	}
	
}
