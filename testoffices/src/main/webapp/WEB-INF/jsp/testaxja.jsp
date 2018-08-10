<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     <input class="btn btn-primary radius" onclick="toSeat()" type="button" onvalue="&nbsp;&nbsp;提交&nbsp;&nbsp;" >

<script type="text/javascript">

function toSeat(){
	$.ajax({
		type:'get',
		url:'<c:url value='/office/showLower'></c:url>',
		data:{
			'depId':1,
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
</body>
</html>