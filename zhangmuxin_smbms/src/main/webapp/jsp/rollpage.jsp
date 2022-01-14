<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>

<body>
<div class="page-bar">
    <input id="totalPageCount" value="${pages}" type="hidden">
    <input id="mark" value="${mark}" type="hidden">
    <ul class="page-num-ul clearfix">
        <li>当前第${page.current}页 共${page.total}条记录&nbsp;&nbsp;
            总共 <span>${pages}页</span>
            <c:if test="${page.current > 1}">
                <a class="pagejump" href="javascript:;" inputPage=1>首页</a>
                <a class="pagejump" href="javascript:;" inputPage=${page.current-1}>上一页</a>
            </c:if>
            <c:if test="${page.current<pages}">
                <a class="pagejump" href="javascript:;" inputPage=${page.current+1}>下一页</a>
                <a class="pagejump" href="javascript:;"
                   inputPage=${pages}>最后一页</a>
            </c:if>
        </li>
    </ul>
    <span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="inputPage" id="inputPage" class="page-key"/>页
	     <button type="button" class="page-btn" onclick="jump_to()">GO</button>
    </span>
</div>
</body>
<script>
    $(function () {
        $(".pagejump").on("click", function () {
            //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            var obj = $(this);
            var mark = document.getElementById("mark").value;
            switch (mark) {
                case "bill":window.location.href =
                    "${pageContext.request.contextPath}/getBillLisByPage?productName=${bill.productName}&providerId=${provider.id}&isPayment=${bill.isPayment}&inputPage=" + obj.attr("inputPage");break;
                case "provider":window.location.href =
                    "${pageContext.request.contextPath}/getProviderLisByPage?proCode=${provider.proCode}&proName=${provider.proName}&inputPage=" + obj.attr("inputPage");break;
                case "user":window.location.href =
                    "${pageContext.request.contextPath}/getUserLisByPage?userCode=${user.userCode}&userName=${user.userName}&gender=${user.gender}&userRole=${user.userRole}&inputPage=" + obj.attr("inputPage");break;
            }
        });
    })
    function jump_to() {
        var num = document.getElementById("inputPage").value;
        var mark = document.getElementById("mark").value;
        //alert(num);
        //验证用户的输入
        var regexp = /^[1-9]\d*$/;
        var totalPageCount = document.getElementById("totalPageCount").value;
        //alert(totalPageCount);
        if (!regexp.test(num)) {
            alert("请输入大于0的正整数！");
            return false;
        } else if ((num - totalPageCount) > 0) {
            alert("请输入小于总页数的页码");
            return false;
        } else {
            switch (mark) {
                case "bill":window.location.href =
                    "${pageContext.request.contextPath}/getBillLisByPage?productName=${bill.productName}&providerId=${provider.id}&isPayment=${bill.isPayment}&inputPage="  + num;break;
                case "provider":window.location.href =
                    "${pageContext.request.contextPath}/getProviderLisByPage?proCode=${provider.proCode}&proName=${provider.proName}&inputPage="  + num;break;
                case "user":window.location.href =
                    "${pageContext.request.contextPath}/getUserLisByPage?userCode=${user.userCode}&userName=${user.userName}&gender=${user.gender}&userRole=${user.userRole}&inputPage="  + num;break;
            }
        }
    }

</script>

<%--
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>
--%>
</html>
