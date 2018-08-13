<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/header.jsp"%>
<html>
<head>
<title>添加用户</title>
</head>
<body>
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form-member-add">
	         <input type="hidden" value="${bannerDto.banner}" id="banner" name="banner">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>ID：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bannerDto.id}" placeholder=""  readonly="readonly" id="id" name="id">
			</div>
		</div>

		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bannerDto.title}" placeholder="" id="title" name="title">
			</div>
		</div >
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">图片：</label>
				<div class="formControls col-xs-8 col-sm-9">
					 <span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" value="${bannerDto.banner}" name="" id=""  style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
						<input type="file" name="iconFile" id="iconFile" multiple name="file-2" class="input-file">
					  </span> 
				</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>连接：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bannerDto.href}" placeholder="http://www.cyhoto.com/banner/list" id="href" name="href">
			</div>
		</div >
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="content" name="content" type="text/plain" style="width:500px;height:150px;">${bannerDto.content}</script>
			</div>
		</div>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${bannerDto.orderby}" placeholder="" id="orderby" name="orderby">
			</div>
		</div >
		
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
<script type="text/javascript">
$(function(){
	//富文本编辑器  
  	var ue = UE.getEditor('content');

		$('.skin-minimal input').iCheck({
			checkboxClass : 'icheckbox-blue',
			radioClass : 'iradio-blue',
			increaseArea : '20%'
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
					url : "<c:url value='/banner/doUpdate'></c:url>",
					dataType : 'json',
					async : false,
					success : function(data) {
						if (data.code == '200') {
							parent.layer.msg("修改成功", {
								icon : 1,
								time : 1000
							});
							parent.$('#btn-refresh').click();
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
	});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>