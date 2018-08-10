<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<html>
<head>
<title>添加图片</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">

		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>员工姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="inp ut-text" value="" placeholder="" id="username" name="username">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>员工编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="number" class="inp ut-text" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/"  placeholder="" id="userid" name="userid">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>员工编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
			     <select id = "buildid"  name="buildid" style="width:200px;" >
                     <c:forEach var="buildList" items="${buildList}" varStatus="s">
                         <option value="${buildList.buildid}" >${buildList.buildname}</option>
                     </c:forEach>
                 </select>  
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>部门：</label>
<!-- 			<div class="formControls col-xs-8 col-sm-9"> -->
<!-- 				<input type="text" class="inp ut-text" value="" placeholder="" id="division" name="division"> -->
<!-- 			</div> -->
                      <div class="col-sm-4">
                          <select class="form-control pull-left" id="parentid" 
                             onchange="getByCupChannelProduct(this.value,'parentid')" name="parentid" style="margin:10px"  >
                             <option value=''>请选择</option> 
		                     <c:forEach var="parentList" items="${parentList}" varStatus="s">
		                         <option value="${parentList.parentid}" >${parentList.parentname}</option>
		                     </c:forEach>
                                               
                         </select>
                       </div>
                          <div class="col-sm-4" id="cup_channel_product_id_div" style="display: none;">
                              <select class="form-control pull-left" id="cup_channel_product_id" name="cup_channel_product_id" style="margin:10px"  >
                              </select>
                          </div>
		</div>
		
	    <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>楼层：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="number" class="inp ut-text" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/"   placeholder="" id="floor" name="floor">
			</div>
		</div>
		
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>房间号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="number" class="inp ut-text" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" id="room" name="room">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">员工图片：</label>
				<div class="formControls col-xs-8 col-sm-9">
					 <span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="iconFile" id="iconFile" placeholder="图片尺寸 1371x560像素" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
						<input type="file" name="iconFile" id="iconFile" multiple name="file-2" class="input-file">
					  </span> 
				</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>座位详情：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="左边数第几列第几排" id="seatdetail" name="seatdetail">
			</div>
		</div>
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<script type="text/javascript">
	
	$("#form-member-add").validate({
		rules:{
			username:{
				required:true,
			},
			userid:{
				required:true,
			},
			iconFile:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			 $(form).ajaxSubmit({
					type : 'post',
					url : "<c:url value='/office/insert'></c:url>",
					dataType : 'json',
					async: false, 
					success : function(data) {
						if (data.code == '200') {
							parent.layer.msg("新增成功",{icon : 1,time : 1000});
// 							alert("sss");
							parent.$('#office-refresh').click();
						  } else if(data.code == '203'){
							  parent.layer.msg("员工已添加",{icon : 1,time : 1000});
//	 							alert("sss");
								parent.$('#office-refresh').click();
						  } else {
							  parent.layer.msg("新增失败",{icon : 1,time : 1000});
						  }
					},
					error : function(XmlHttpRequest,textStatus,errorThrown) {
							    layer.msg('error!',{icon : 1,time : 1000});
						    }
					});
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
		}
	});
	
	
	function getByCupChannelProduct(parentid,id){
	    var obj = $("#"+id);
	    if(parentid == ''){
	        obj.empty();
	    }else{
	        var obj = $("#"+id);
	        $.post(
	        		"<c:url value='/office/showLower'></c:url>", 
	        		{"parentid":parentid}, 
	        		function (data) {
	                   if (data.code != '200') {
//	     /**样式 二级的点击有就显示  无就不显示
	                $("#cup_channel_product_id_div").css("display","none");
	            } else {
	                $("#cup_channel_product_id_div").css("display","block");
	                var value = data.msg;
	                var len = value.length;
	               $("#cup_channel_product_id").empty();
	              $("#cup_channel_product_id").append("<option value=''>请选择</option>");
	                for(var i=0;i<len;i++){
	                	 $("#cup_channel_product_id").append("<option value='"+value[i].id+"'>"+value[i].lowername+"</option>");
	                }
	            }
	        }, 'json');
	    }
	}
	
	
	
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>