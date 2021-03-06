package cn.itcast.bos.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Function entity. @author MyEclipse Persistence Tools
 */

public class Function implements java.io.Serializable {

	// Fields

	private String id;
	private Function function;//父类权限
	private String name;
	private String code;
	private String description;
	private String page;
	private String generatemenu;
	private Integer zindex;//优先级
	private Set functions = new HashSet(0);//子类权限集合
	private Set roles = new HashSet(0);//拥有该权限的角色集合
	//添加一个父类的权限id，以便于在页面上显示
	private String pId;
	// Constructors

	public String getpId() {
		
		if(function != null){
			return function.getId();
		}else{
			return "0";
		}
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	/** default constructor */
	public Function() {
	}

	/** minimal constructor */
	public Function(String id) {
		this.id = id;
	}

	/** full constructor */
	public Function(String id, Function function, String name, String code,
			String description, String page, String generatemenu,
			Integer zindex, Set functions, Set roles) {
		this.id = id;
		this.function = function;
		this.name = name;
		this.code = code;
		this.description = description;
		this.page = page;
		this.generatemenu = generatemenu;
		this.zindex = zindex;
		this.functions = functions;
		this.roles = roles;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Function getFunction() {
		return this.function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getGeneratemenu() {
		return this.generatemenu;
	}

	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}

	public Integer getZindex() {
		return this.zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public Set getFunctions() {
		return this.functions;
	}

	public void setFunctions(Set functions) {
		this.functions = functions;
	}

	public Set getRoles() {
		return this.roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}

}