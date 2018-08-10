<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./common/taglib.jsp"%>
<%@ include file="./common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>deoartmnet</title>
</head>
<body>

  <table>
   <td style="width: 40%;">
                                    <label class="col-sm-3 control-label">部门</label>
                                    <div class="col-sm-4">
                                        <select class="form-control pull-left" id="parentid" 
//点击第一个框时调用第二个下拉框的方法                        onchange="getByCupChannelProduct(this.value,'parentid')" name="parentid" style="margin:10px"  >
                                   <option value=''>请选择</option> 
         <c:forEach var="parentList" items="${parentList}" varStatus="s">
             <option value="${parentList.parentid}" >${parentList.parentname}</option>
         </c:forEach>
                                  
                           </select>
                       </div>
                       <label class="col-sm-1 control-label"></label>
                       <div class="col-sm-4" id="cup_channel_product_id_div" style="display: none;">
                           <select class="form-control pull-left" id="cup_channel_product_id" name="cup_channel_product_id" style="margin:10px"  >
                           </select>
                       </div>
                   </td>
     </table>
</body>
<script type="text/javascript">
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
//     /**样式 二级的点击有就显示  无就不显示
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
</html>