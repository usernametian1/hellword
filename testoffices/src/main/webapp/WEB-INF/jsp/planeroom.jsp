<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

  <style type="text/css">
	#bottom{ height:350px; width:380px; margin-top:5px; margin-left:9px;}
	.b_r_div{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div1{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div2{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div3{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div4 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div5 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div6 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div7 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div8 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div9 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div10 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div11 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div12 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div13 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	#b_r_bottom{ height:120px; width:225px; margin-left:5px; float:left; margin-top:5px; border:1px solid #000000;}
</style>
</head>
<body>
<div id="containt">
<!-- 		<div id="top"> -->
<!-- 			<div id="top_left"></div> -->
<!-- <!-- 			<div id="top_right"></div> -->
<!-- 		</div> -->
<!-- 		<div id="content"></div> -->
		<div id="bottom">
<!-- 			<div id="bom_left"></div> -->
<!-- 			<div id="bom_right"> -->
				<div class="b_r_div1"></div>
				<div class="b_r_div2"></div>
				<div class="b_r_div3"></div>
				<div class="b_r_div4"></div>
				<div class="b_r_div5"></div>
				<div class="b_r_div6"><div>
<!-- 				<div class="b_r_div7">07</div> -->
<!-- 				<div class="b_r_div8">08</div> -->
<!-- 				<div class="b_r_div9">09</div> -->
<!-- 				<div class="b_r_div10">10</div> -->
<!-- 				<div class="b_r_div12">11</div> -->
<!-- 				<div class="b_r_div12">12</div> -->
<!-- 				<div class="b_r_div13">13</div> -->
<!-- 				<div id="b_r_bottom"></div> -->
<!-- 			</div> -->
		</div>
	</div>
	
<script type="text/javascript">
   var roomid = ${roomid};
	$(function() {
	    if(roomid == '01'){
// 	    	$(".b_r_div1").style.background="blue";
	    	$(".b_r_div1").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }else if(roomid == '02'){
	    	$(".b_r_div2").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }else if(roomid == '03'){
	    	$(".b_r_div3").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }else if(roomid == '04'){
	    	$(".b_r_div4").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }else if(roomid == '05'){
	    	$(".b_r_div5").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }else if(roomid == '06'){
	    	$(".b_r_div6").css({"background-color":"hsla(0, 0%, 62%, 0.43)"});
	    }
	   
	});
   

</script> 
</body>
</html>