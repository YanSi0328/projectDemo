<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增商品信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>


    <style>
        .mainPanel {
            width: 50%;
            margin: 100px auto;
        }
    </style>
</head>
<body>
<div class="mainPanel">
    <form action="${pageContext.request.contextPath}/prod/addProd" method="post">
        <div class="form-group">
            <label for="prodId">商品ID</label>
            <input type="text" class="form-control" id="prodId" name="prodId" placeholder="prodId">
        </div>
        <div class="form-group">
            <label for="prodName">商品名称</label>
            <input type="text" class="form-control" id="prodName" name="prodName" placeholder="prodName">
        </div>
        <div class="form-group">
            <label for="prodPrice">商品单价</label>
            <input type="text" class="form-control" id="prodPrice" name="prodPrice" placeholder="prodPrice">
        </div>
        <div class="form-group">
            <label for="prodImg">商品图片地址</label>
            <input type="text" class="form-control" id="prodImg" name="prodImg" placeholder="prodImg">
        </div>
        <div class="form-group">
            <label for="prodDesc">商品描述</label>
            <input type="text" class="form-control" id="prodDesc" name="prodDesc" placeholder="prodDesc">
        </div>

        <button type="submit" class="btn btn-default">保存</button>
        <a href="${pageContext.request.contextPath}/prod/query" type="button" class="btn btn-default">返回</a>
    </form>
</div>

</body>
</html>
