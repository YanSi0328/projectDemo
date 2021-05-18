<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>修改商品信息</title>
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
    <h3>请录入要修改的商品信息</h3>
    <form action="${pageContext.request.contextPath}/prod/updateProd" method="post">
        <div class="form-group">
            <label for="prodId">商品ID</label>
            <input type="text" class="form-control" id="prodId" value="${prodById.pid}" readonly name="prodId" placeholder="prodId">
        </div>
        <div class="form-group">
            <label for="prodName">商品名称</label>
            <input type="text" class="form-control" id="prodName" value="${prodById.pname}" name="prodName"  placeholder="prodName">
        </div>
        <div class="form-group">
            <label for="prodPrice">商品单价</label>
            <input type="text" class="form-control" id="prodPrice" value="${prodById.price}" name="prodPrice"  placeholder="prodPrice">
        </div>
        <div class="form-group">
            <label for="prodImg">商品图片地址</label>
            <input type="text" class="form-control" id="prodImg" value="${prodById.pimg}" name="prodImg"  placeholder="prodImg">
        </div>
        <div class="form-group">
            <label for="prodDesc">商品描述</label>
            <input type="text" class="form-control" id="prodDesc" value="${prodById.pdesc}" name="prodDesc"  placeholder="prodDesc">
        </div>

        <button type="submit" class="btn btn-default">保存</button>
    </form>
</div>

</body>
</html>
