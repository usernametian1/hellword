<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
            <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    application.setAttribute("basePath",basePath);
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="multipart/form-data;         charset=utf-8" />
<title>添加</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.seat-charts.min.js"></script>

<style type="text/css">
body { 
font-family:Arial, Helvetica, sans-serif; 
font-size:12px; 
margin:0; 
} 
#main { 
height:1000px; 
padding-top:90px; 
text-align:center; 
} 
#fullbg { 
background-color:gray; 
left:0; 
opacity:0.5; 
position:absolute; 
top:0; 
z-index:3; 
filter:alpha(opacity=50); 
-moz-opacity:0.5; 
-khtml-opacity:0.5; 
} 
#dialog { 
background-color:#fff; 
border:5px solid rgba(0,0,0, 0.4); 
height:500px; 
left:35%; 
margin:-200px 0 0 -200px; 
padding:1px; 
position:fixed !important; /* 浮动对话框 */ 
position:absolute; 
top:50%; 
width:800px; 
z-index:5; 
border-radius:5px; 
display:none; 
} 
#dialog p { 
margin:0 0 12px; 
height:24px; 
line-height:24px; 
background:#CCCCCC; 
} 
#dialog p.close { 
text-align:right; 
padding-right:10px; 
} 
#dialog p.close a { 
color:#fff; 
text-decoration:none; 
} 
</style>
</head>

<body>
<div id="main">
	<form action="add.do" method="post" enctype="multipart/form-data" onsubmit="return checkWorker();" >
		工号：<input type="text" name="id" id="id" ><br>
		姓名：<input type="text" name="name" id="name" value="${worker.name} "><br>
		性别：<input id="man" type="radio" checked="checked" name="sex" value="1" />男
		     <input id="woman" type="radio"  name="sex" value="0"/>女 <br>
		座机：<input type="text" name="phone" value="${worker.phone} "><br>
		手机：<input type="tel" name="telephone" id="telephone" value="${worker.telephone}"><br>
		岗位：<input type="text" name="station" value=" ${worker.station }"><br>
		邮箱：<input type="email" name="email" value="${worker.email }"><br>
		照片：<c:if test="${worker.photo==null || worker.photo=='' }">
					<img src="${pageContext.request.contextPath }/img/timg.jpg" width="150px" height="100px"/>
			</c:if> 
			<c:if test="${worker.photo!=null && worker.photo!='' }">
							<img src="${pageContext.request.contextPath }/upload/${worker.photo}"
										width="150px" height="100px" />
			</c:if> <%--  <input type="file" name="photo" value="${workers.photo }"> --%> 
			<input id="file-1" type="file" name="s_photo" multiple class="file"
							data-overwrite-initial="false" data-min-file-count="2" value="${worker.photo }"> </br> 
		所属楼：<input type="text" name="house" value="${worker.house }">
		楼层：<input type="text" name="floor" value="${worker.floor }"><br>
		部门：
		<select name="depId"  id="select">
			<option value="0">请选择所属部门</option>
			<c:forEach items="${dept}" var="dept">
				<option value='${dept.depId}' >${dept.depName}</option>
			</c:forEach>
		</select>
		<!-- <a id="layout" href="javascript:void(0);" onclick="addLayout()" >选择部门工位</a><br> -->
		<a href="javascript:showBg();">选择部门工位</a><br>
		<input type="hidden" name="seat" id="seat" value="${worker.seat }">
		<%-- 具体位置：<input type="text" name="seat" id="seat" value="${worker.seat }"><br> --%>
		<input type="submit" value="提交"  >
		<input type="reset" value="重置">
		<input type="button" value="取消"   onclick="exit()" >
	</form>
<!-- <div id="main"><a href="javascript:showBg();">点击这里查看效果</a>  -->
<div id="fullbg"></div> 
<div id="dialog"> 
<p class="close"><a href="#" onclick="closeBg();">关闭</a></p> 
<div>
<%@ include file="seat.jsp" %>
</div> 
</div> 
</div> 
	
</body>
<script type="text/javascript">
		function checkWorker() {
			var id=document.getElementById("id").value;
			var n=document.getElementById("name").value;
			var tel=document.getElementById("telephone").value;
			var s=document.getElementById("seat").value;
			if(id==""||id==null){
				alert("工号不能为空");
				return false;
			}
			if(n==""||n==null){
				alert("用户名不能为空");
				return false;
			}
			if(tel==""||tel==null){
				alert("手机号不能为空");
				return false;
			}
			if(s==""||tel==null){
				alert("具体位置不能为空");
				return false;
			}
				return true;
			
		}

		function exit(){
			
			window.location.href="${basePath}/detail.do";
		}
		
		function addLayout(){
			var id=$('#select option:selected').val(); //获取下拉框中的值
			window.location.href="${basePath}/toSeat.do?depId="+id;
		}
		//显示灰色 jQuery 遮罩层 
		function showBg() {
			var id=$('#select option:selected').val();
			alert("id="+id);
			var bh = $("body").height(); 
			var bw = $("body").width(); 
			$("#fullbg").css({ 
			height:bh, 
			width:bw, 
			display:"block" 
			});
			
			toSeat();
			
			$("#dialog").show();
			
		} 
		//关闭灰色 jQuery 遮罩 
		function closeBg() { 
		$("#fullbg,#dialog").hide(); 
		}
		
		function toSeat(){
			var id=$('#select option:selected').val();
			$.ajax({
				type:'get',
				url:'${basePath}/toSeat.do',
				data:{
					'depId':id,
				},
				cache:"false",
				async:"true",
				dataType:"json",
				success:function(data) {
					alert("123456");
					alert("msg="+data);
					//var str="<input type='hidden' id='map' value='"+msg.layout+"'>";
					

					//$("#main").html(str);
				}
			
			})
		}
		

		
		
		
		
</script>
</html>