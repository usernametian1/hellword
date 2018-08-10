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

			<div class="formControls col-xs-8 col-sm-9">
				<input type="hidden" class="inp ut-text" value="${userid }" id="userid" name="userid">
			</div>
		
		<div class="row cl">
				<div class="formControls col-xs-8 col-sm-9">
					 <span class="btn-upload form-group">
						<input class="input-text upload-url" type="text" name="iconFile" id="iconFile" placeholder="图片尺寸 1371x560像素" style="width:200px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
						<input type="file" name="iconFile" id="iconFile" multiple name="file-2" class="input-file">
					  </span> 
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
					url : "<c:url value='/office/doPortrait'></c:url>",
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
	
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>