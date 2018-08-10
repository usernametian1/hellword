<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<title>房间</title>
<style type="text/css">
	#containt{ height:530px; width:400px; margin:0 auto; border:1px solid #000000;}
	#top{ height:100px; width:380px; margin-top:7px; margin-left:9px;}
	#top_left{ height:100px; width:138px; float:left; border:1px solid #000000; } 
	#top_right{ height:100px; width:238px; float:left; border:1px solid #000000;}
 	#content{ height:100px; width:138px; margin-top:5px; margin-left:9px; border:1px solid #000000;} 
	#bottom{ height:350px; width:380px; margin-top:5px; margin-left:9px;}
	#bom_left{ height:100px; width:138px; float:left; border:1px solid #000000;}
	#bom_right{ height:350px; width:235px; float:left; border:1px solid #000000; margin-left:3px;}
	.b_r_div{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div1{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div2{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div3{ height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div4 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div5 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	.b_r_div6 { height:100px; width:138px; margin-left:6px; float:left; margin-top:5px; border:1px solid #000000;}
	#b_r_bottom{ height:120px; width:225px; margin-left:5px; float:left; margin-top:5px; border:1px solid #000000;}
</style>
</head>
<body>
	<div id="containt">
<!-- 		<div id="top"> -->
<!-- 			<div id="top_left"></div> -->
<!-- <!-- 			<div id="top_right"></div> --> -->
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
				<div class="b_r_div6"></div>
<!-- 				<div id="b_r_bottom"></div> -->
<!-- 			</div> -->
		</div>
	</div>
	
<script type="text/javascript">
   var roomid = ${roomid};
	$(function() {
	    if(roomid == '01'){
	    	$(".b_r_div1").toggleClass("blue");
	    }
	});
   

</script> 
</body>
</html>