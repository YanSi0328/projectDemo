<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>商品信息页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js" type="text/javascript"
            charset="utf-8"></script>
    <style>
        .mainDiv {
            width: 80%;
            margin: 100px auto;
        }
    </style>
</head>
<body>
<div class="mainDiv">
    <form action="${pageContext.request.contextPath}/prod/query" method="post" class="form-inline">
        <div class="form-group">
            <label for="prodid">商品id</label>
            <input type="text" class="form-control" id="prodid" name="prodid" value="${queryProd.pid}"
                   placeholder="prodid">
        </div>
        <div class="form-group">
            <label for="prodname">商品名称</label>
            <input type="text" class="form-control" id="prodname" name="prodname" value="${queryProd.pname}"
                   placeholder="prodname">
        </div>
        <button type="submit" class="btn btn-info">查询</button>
        <button id="addBtn" type="button" class="btn btn-success">添加</button>
    </form>
    <table class="table">
        <caption>商品信息表</caption>
        <thead>
        <tr>
            <th>商品编号</th>
            <th>商品名称</th>
            <th>商品价格</th>
            <th>商品图片</th>
            <th>商品描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="msg">
        <%-- 循环遍历在用户集合中取值 --%>
        <c:forEach items="${prodList}" var="prod">
            <tr>

                <td>${prod.pid}</td>
                <td>${prod.pname}</td>
                <td>${prod.price}</td>
                <td>${prod.pimg}</td>
                <td>${prod.pdesc}</td>
                <td>
                    <input type="button" prodId="${prod.pid}" value="修改" class="btn btn-danger editBtn">
                    <input type="button" prodId="${prod.pid}" value="删除" class="btn btn-danger delBtn">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input id="prevBtn" type="button" class="btn btn-default" value="上一页">
    <input id="nextBtn" type="button" class="btn btn-default" value="下一页">

    当前第 ${page} 页 每页显示 ${pageSize} 条记录 总共 ${totalPage} 页
</div>
</body>
<script>
    var page = ${page};
    var totalPage = ${totalPage};

    // 检查状态：当前页数小于一页时，禁用向前查找；当前页数大于总页数时，禁用向后查找
    function checkBtn() {
        // console.log("page " + page);
        // console.log("totalPage " + totalPage);
        // 如果没有查到数据，给用户一个提示
        if (totalPage == 0) {
            console.log("查无数据");
            var content = " <span>商品编号为：" + $("#prodid").val()
                + "<br>商品名称为：" + $("#prodname").val()
                + "<br>查无数据，请核对后输入！</span>";
            $("#msg").append(content);
        }
        if (page <= 1) {
            $("#prevBtn").prop("disabled", true);
        }
        if (page >= totalPage) {
            $("#nextBtn").prop("disabled", true);
        }
    }

    checkBtn();

    // 向前查找事件
    $("#prevBtn").click(function () {
        // 修改page的值
        location.href = "${pageContext.request.contextPath}/prod/query?page=${page-1}&pageSize=${pageSize}"
            + "&prodid=" + $("#prodid").val() + "&prodname=" + $("#prodname").val();
    });
    // 向后查找事件
    $("#nextBtn").click(function () {
        // 修改page的值
        location.href = "${pageContext.request.contextPath}/prod/query?page=${page+1}&pageSize=${pageSize}"
            + "&prodid=" + $("#prodid").val() + "&prodname=" + $("#prodname").val();
    });

    // 新增商品事件
    $("#addBtn").click(function () {
        // 跳到指定路径
        location.href = "${pageContext.request.contextPath}/product/addProd.jsp";
    });
    // 修改商品事件
    $(".editBtn").click(function () {
        // 拿到当前商品的id
        var pid = $(this).attr("prodId");
        location.href = "${pageContext.request.contextPath}/prod/selectProdById?pid=" + pid;
    });
    // 删除商品事件
    $(".delBtn").click(function () {
        var pid = $(this).attr("prodId");
        location.href = "${pageContext.request.contextPath}/prod/delProdById?pid=" + pid;
    });

</script>
</html>