<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- 引入easyui相关的资源文件 -->
	<link rel="stylesheet" type="text/css"
	 href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<!-- 引入ztree资源文件 -->
	 <link rel="stylesheet"
	  href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#grid").datagrid({
				
				columns :[ [  {field:'id',title:'编号',checkbox:true},
				             {field:'name',title:'姓名',
									//编辑功能,需要开启
									editor:{
										type:'validatebox',
										options:{
											required:true
										}
									}
				             
				             },
				             {field:'age',title:'年龄',
				            		editor:{
										type:'validatebox',
										options:{
											required:true
										}
									}	 
				             }
				             
				             ]],
				
				url:'${pageContext.request.contextPath }/json/data.json',
				toolbar:[
				         {text:'新增一行',iconCls:'icon-add'},
				         {text:'编辑',iconCls:'icon-edit',handler:function(){
				       
				        	 //将当前选中的行开启编辑状态
				        	 var rows=$("#grid").datagrid("getSelections");
				        	 if(rows.length == 1){
				        		 //表示选中了一行，开启该行的编辑状
				        		 //获得当中选中行的索引
				        		 var index = $("#grid").datagrid("getRowIndex",rows[0]);
				        		
				        		 //开启编辑状态
				        	      $("#grid").datagrid("beginEdit",index);
				        		 
				        	 }
				        	 
				         }},
				         {text:'保存',iconCls:'icon-add'}
				         
				         ]
			});
		});
	
	
	</script>
	
</head>
<body >
<table id="grid"></table>
	
</body>
</html>