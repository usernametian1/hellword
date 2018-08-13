<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/header.jsp"%>
<html>
<head>
<title>办公位管理</title>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>图片管理
		<span class="c-gray en">&gt;</span>办公位列表 <a
			class="btn btn-success radius r" id="btn-refresh"
			style="line-height: 1.6em; margin-top: 3px"
			onclick="RefreshWebPage();"
			href="javascript:location.replace(location.href);" title="刷新"> <i
			class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
</head>
<body>
<form id="stateForm" action="<c:url value='/offices/list'></c:url>" method="post">
<div class="page-container">
	<div class="text-c"> 
		<input type="text" class="input-text" style="width:250px" placeholder="输入ID,标题" id="bannerpnn" name="bannerpnn">
		<button type="submit" class="btn btn-success radius" id="" name=""><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="member_add('添加用户','<c:url value='/banner/rInsert'></c:url>','','510')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加图片</a></span></div>
	<div class="mt-20">
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr class="text-c">
<!-- 				<th width="25"><input type="checkbox" name="" value=""></th> -->
				<th width="80">员工编号</th>
				<th width="100">员工姓名</th>
				<th width="40">楼层</th>
				<th width="100">房间号</th>
<!-- 				<th width="100">图片</th> -->
                <th width="100">座位详情</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="item" items="${officeList}" varStatus="status">
			<tr class="text-c">
<%-- 				<td><input type="checkbox" value="${item.id}" name="upgrade"></td> --%>
				<td>${item.userid }</td>
				<td>${item.username}</td>
				<td>${item.floor }</td>
				<td>${item.room }</td>
				<td>${item.seatdetail }</td>
<%-- 			<td><img width="60" class="product-thumb" src="${item.bannerUrl}"></td> --%>
				<td class="td-manage"> 
				  <a title="编辑" href="javascript:;" onclick="member_edit('编辑','<c:url value='/banner/redit/${item.id}'/>','','','510')" 
				     class="ml-5" style="text-decoration:none">
				  <i class="Hui-iconfont">&#xe6df;</i></a>
				  <a title="删除" href="javascript:;" onclick="member_del(this,'${item.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	</div>
<div class="page">
			<myTag:paging paging="${paging}" url="/offices/list" formId="stateForm" />
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
		layer.msg("请选择要删除的产品  ");
		return 
	}
	
	var values = value.toString();
   	layer.confirm('确认要删除吗？',function(index){
//    		window.location="<c:url value='/product/batchesdelete/'/>"+values;
   		window.location="<c:url value='/banner/batchesDelete/'/>"+values;
    });
	
}


/*banner-下架*/
function product_stop(obj ,id){
	layer.confirm('确认要下架吗？',function(index){
// 		alert(xiajia)
		$.ajax({
			type: 'POST',
			url: "<c:url value='/banner/unDercarRiage'></c:url>",
			data:{'id':id},
			dataType: 'json',
			success: function(data){
// 				$(obj).parents("tr").remove();
				if(data.code == 200){
					$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="product_start(this,'+data.msg+')" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已下架</span>');
					$(obj).remove();
					layer.msg('已下架!',{icon: 5,time:1000});

				}else{
					$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="product_stop(this,'+data.msg+')" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已发布</span>');
					$(obj).remove();
					layer.msg('下架失败!' ,{icon: 6,time:1000});
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
		
		});
}

/*banner-发布*/
function product_start(obj,id){
	layer.confirm('确认要发布吗？',function(index){
		$.ajax({
			type: 'POST',
			url: "<c:url value='/banner/Release'></c:url>",
			data:{'id':id},
			dataType: 'json',
			success: function(data){
				if(data.code == 200){
					$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="product_stop(this,'+data.msg+')" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已发布</span>');
					$(obj).remove();
					layer.msg('已发布!',{icon: 6,time:1000});
					
				}else{
					$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="product_start(this,'+data.msg+')" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
					$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已下架</span>');
					$(obj).remove();
					layer.msg('发布失败! ',{icon: 5,time:1000});
				}
			},
			error:function(data) {
				console.log(data.msg);
			},
		});	
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
/*用户-停用*/
function member_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_start(this,id)" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已停用</span>');
				$(obj).remove();
				layer.msg('已停用!',{icon: 5,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

/*用户-启用*/
function member_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '',
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="member_stop(this,id)" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>');
				$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
				$(obj).remove();
				layer.msg('已启用!',{icon: 6,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}
/*用户-编辑*/
function member_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*密码-修改*/
function change_password(title,url,id,w,h){
	layer_show(title,url,w,h);	
}

function member_del(obj,id){
		layer.confirm('你确认要删除此吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				window.location="<c:url value='/banner/deleteBanner/'/>"+id;
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