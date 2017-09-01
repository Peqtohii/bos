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
	 	<!--创建ztree使用树 -->
	 	var setting ={};
	 	var zNodes =[
	 	             {name:'节点1',children:[{name:'节点11',children:[
	 	                                                          { name:'节点111'},
	 	                                                          { name:'节点112'}
	 	                                                          ]
	 	            							 },{name:'节点12'}
	 	                                   ]},
	 	                                   {name:'节点2'}
	 	                                   
	 	             ];
	 	//初始化树 
	 	$(function(){
	 		$.fn.zTree.init($("#myTree"),setting,zNodes);
	 	});
	 
	 </script>
</head>
<body class="easyui-layout">
	<!-- 每个div是一个区域 -->
	<div data-options="region:'north',title:'XXX管理系统'" style="height: 150px">北部区域</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 200px">
		<!-- 折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div是一个面板 -->
			
			<div data-options="iconCls:'icon-search'" title="面板一">
			
				<a onclick="addTabs();" id="baidulink" class="easyui-linkbutton">百度</a>
			</div>
			<div title="面板二">
				<ul id="myTree" class="ztree"></ul>
			</div>
			<div title="面板三">
			<script type="text/javascript">
				
			
			</script>
				<ul id="myTree2" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 选项卡面板 -->
		<div id="tabs" class="easyui-tabs" data-options="fit:true">
			<!-- 每个子div是一个选项卡 -->
			<div data-options="closable:true,iconCls:'icon-reload'" title="选项卡一">
				选项卡一内容
			</div>
			<div title="选项卡二">
				选项卡二内容
			</div>
			<div title="选项卡三">
				选项卡三内容
			</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>