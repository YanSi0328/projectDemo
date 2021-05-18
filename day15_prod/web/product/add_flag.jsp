<%--
  Created by IntelliJ IDEA.
  User: JAVASM
  Date: 2021/5/17
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品信息</title>
</head>
<body>
${addMsg}
<hr>
<br>
<span>页面 2 秒后跳转</span>
</body>

<script>
    setTimeout(function(){
        location.href = "${pageContext.request.contextPath}/prod/query"
    },2000)


</script>
</html>
