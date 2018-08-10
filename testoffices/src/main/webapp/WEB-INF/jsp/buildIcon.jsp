<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户头像</title>
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
   
	<img class="avatar size-XL " src="${build.buildUrl}">
    
<%-- 	<img width="60" class="product-thumb" src="${item.avatar}"> --%>
	<dl style="margin-left:80px; color:#fff">
		<dt>
			<span class="f-18">${build.buildname}</span>
		</dt>
	</dl>
</div>
</body>
</html>