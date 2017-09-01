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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function addTabs() {
		$("#tabs").tabs("add",{
			iconCls:'icon-help',
			closable:true,
			title:'百度',
			content:'啦啦'
		}
		);
		}

</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'north'" title="欢迎" style="height: 150px"></div>
		<div data-options="region:'west'" title='系统菜单' style="width: 200px">
			<div class="easyui-accordion" data-options="fit:true">
				<div data-options="iconCls:'icon-search'" title="面板一">
					<a onclick="addTabs();" class="easyui-linkbutton">百度</a>
					
				</div>
					<div title="面板w">
					1
				</div>
					<div title="面板w2">
					1
				</div>
			</div>
		
		</div>
		<div data-options="region:'center'" title='内容' >
			<div id="tabs" class="easyui-tabs" data-options="fit:true">
			<!-- 每个div是一个选项卡 -->
				<div 
					data-options="closable:true,iconCls:'icon-reload'"
					title="选项卡1"
				>
				内容
				</div>
				
			</div>
		</div>
		<div data-options="region:'east'" style="width: 100px">东部区域</div>
		
</body>
</html>