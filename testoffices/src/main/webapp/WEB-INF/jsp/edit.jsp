<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<html>
<head>
<title>编辑员工</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
	         <input type="hidden" value="${office.userid}" id="userid" name="userid">

		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>员工姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${office.username}" placeholder="" id="username" name="username">
			</div>
		</div >
		
	   <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>所在办公楼：</label>
			<div class="formControls col-xs-8 col-sm-9">
			   <select id = "buildid"  name="buildid" style="width:200px;" >
                              <c:forEach var="item" items="${buildList}" varStatus="s">
                                  <option value="${item.buildid}" <c:if test="${office.buildid eq item.buildid}">selected</c:if>>${item.buildname}</option>
                              </c:forEach>
                  </select>  
			</div>
		</div >
		
<!-- 		<div class="row cl"> -->
<!-- 			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>部门：</label> -->
<!-- 			<div class="formControls col-xs-8 col-sm-9"> -->
<%-- 				<input type="text" class="inp ut-text" value="${office.division}" placeholder="" id="division" name="division"> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>部门：</label>
			<div class="formControls col-xs-8 col-sm-9">
			   <select class="form-control pull-left" id="parentid" 
                             onchange="getByCupChannelProduct(this.value,'parentid')" name="departmentid" style="margin:10px"  >
                             <option value=''>请选择</option> 
		                     <c:forEach var="departMent" items="${departMent}" varStatus="s">
		                         <option value="${departMent.parentid}" <c:if test="${office.departmentid eq departMent.parentid}">selected</c:if>>${departMent.parentname}</option>
		                     </c:forEach>
                         </select>
			</div>
			<div class="col-sm-4" id="cup_channel_product_id_div" style="display: none;">
                              <select class="form-control pull-left" id="cup_channel_product_id" name="sonpartmentid" style="margin:10px"  >
                              </select>
                          </div>
		</div >
		
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>楼层：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="inp ut-text" value="${office.floor}" placeholder="" id="floor" name="floor">
			</div>
		</div>
		
		 <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>房间号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="inp ut-text" value="${office.room}" placeholder="" id="room" name="room">
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>座位详情：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${office.seatdetail}"  id="seatdetail" name="seatdetail">
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
$(function(){
          getByCupChannelProduct(this.value,'parentid');
//           window.load=getByCupChannelProduct(this.value,'parentid');
//           $(option).click();
          
});
	     
	
		$("#form-member-add").validate({
			rules : {
			// 			id:{
			// 				required:true,
			// 			},
			// 			title:{
			// 				required:true,
			// 			},
			// 			banner:{
			// 				required:true,
			// 			},
			// 			orderby:{
			// 				required:true,
			// 			},
			},
			onkeyup : false,
			focusCleanup : true,
			success : "valid",
			submitHandler : function(form) {
				$(form).ajaxSubmit({
					type : 'post',
					url : "<c:url value='/office/doUpdate'></c:url>",
					dataType : 'json',
					async : false,
					success : function(data) {
						if (data.code == '200') {
							parent.layer.msg("修改成功", {
								icon : 1,
								time : 1000
							});
							parent.$('#office-refresh').click();
						} else {
							parent.layer.msg("修改失败", {
								icon : 1,
								time : 1000
							});
						}
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						layer.msg('error!', {
							icon : 1,
							time : 1000
						});
					}
				});
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});

		
		function getByCupChannelProduct(parentid,id){
			var sonpartmentid = ${office.sonpartmentid};
		    var obj = $("#"+id);
		    if(parentid == ''){
		        obj.empty();
		    }else{
		    	
		        var obj = $("#"+id);
		        parentid = $("#parentid").val();
		        
		        $.post(
		        		"<c:url value='/office/showLower'></c:url>", 
		        		{"parentid":parentid}, 
		        		function (data) {
		                   if (data.code != '200') {
//		     /**样式 二级的点击有就显示  无就不显示
		                $("#cup_channel_product_id_div").css("display","none");
		            } else {
		                $("#cup_channel_product_id_div").css("display","block");
		                var value = data.msg;
		                var len = value.length;
		               $("#cup_channel_product_id").empty();
		              $("#cup_channel_product_id").append("<option value=''>请选择</option>");
		                for(var i=0;i<len;i++){
		                	 
		                			 if(sonpartmentid == value[i].id ){
		                				 $("#cup_channel_product_id").append("<option value='"+value[i].id+
		                						 "' selected = 'selected'>"+value[i].lowername+"</option>");
		                			 }else{
		                				 $("#cup_channel_product_id").append("<option value='"+value[i].id+
		                						 "'>"+value[i].lowername+"</option>");
		                			 }
		                			
		                }
		            }
		        }, 'json');
		    }
		}
		
		
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>