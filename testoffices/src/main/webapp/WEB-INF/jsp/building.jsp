<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<html>
<head>
<title>办公位管理</title>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>办公位管理
		<span class="c-gray en">&gt;</span>办公楼 <a
			class="btn btn-success radius r" id="office-refresh"
			style="line-height: 1.6em; margin-top: 3px"
			onclick="RefreshWebPage();"
			 title="刷新"> <i
			class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
</head>
<body>
<form id="stateForm" action="<c:url value='/office/build'></c:url>" method="post">
<div class="page-container">
	<div class="text-c"> 
		<input type="text" class="input-text" style="width:250px" placeholder="输入办公楼" id="bannerpnn" name="bannerpnn"/>
		<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="member_add('添加员工','<c:url value='/office/rbuild'></c:url>','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加办公楼</a></span></div>
	<div class="mt-20">
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr class="text-c">
			    <th width="40"><input name="upgrade" value="" type="checkbox" value=""></th>
<!-- 				<th width="25"><input type="checkbox" name="" value=""></th> -->
				<th width="80">办公楼</th>
                <th width="100">头像</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="item" items="${buildList}" varStatus="status">
			<tr class="text-c">
			    <td><input name="upgrade" type="checkbox" value="${item.buildid}"></td>
<%-- 				<td><input type="checkbox" value="${item.id}" name="upgrade"></td> --%>
				<td>${item.buildname}</td>
			  <td><img width="60" class="product-thumb" src="${item.buildUrl}"></td>
				<td class="td-manage"> 
				  <a title="编辑" href="javascript:;" onclick="member_edit('编辑','<c:url value='/office/RUpdate/${item.buildid}'/>','','','510')" 
				     class="ml-5" style="text-decoration:none">
				  <i class="Hui-iconfont">&#xe6df;</i></a>
				  <a title="删除" href="javascript:;" onclick="member_del(this,'${item.buildid}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	</div>
<div class="page">
			<myTag:paging paging="${paging}" url="/office/build" formId="stateForm" />
		</div> 
</div>
</form>
<script type="text/javascript">

function  datadel(){
	var tests = document.getElementsByName('upgrade');
	var value = new Array();
	for(var i = 0; i < tests.length;i++){
		if(tests[i].checked){
			value.push(tests[i].value);
		}
	}
	if(value.length <= 0){
		layer.msg("请选择要删除的员工");
		return 
	}
	
	var values = value.toString();
   	layer.confirm('确认要删除吗？',function(index){
//    		window.location="<c:url value='/product/batchesdelete/'/>"+values;
   		window.location="<c:url value='/office/batchesDelete/'/>"+values;
    });
	
}


$(function(){
	$('.table-sort').dataTable({
		"aaSorting": [[ 1, "desc" ]],//默认第几个排序
		"bStateSave": true,//状态保存
		"aoColumnDefs": [
		  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
		]
	});
	
});
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-查看*/
function member_show(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*用户-编辑*/
function member_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*密码-修改*/
function change_password(title,url,id,w,h){
	layer_show(title,url,w,h);	
}

function member_del(obj,userid){
		layer.confirm('你确认要删除此吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				window.location="<c:url value='/office/deleteBuild/'/>"+userid;
			}, function(){
			  layer.msg('操作已取消', {
			    time: 1000 //20s后自动关闭
			  });
			});
}

function RefreshWebPage(){
	setTimeout(function(){
			location.replace(location.href);
		},1000);
}
</script> 
</body>
</html>