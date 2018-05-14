<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>HystrixAdding</title>
    <!-- Normalize -->
    <link href="${pageContext.request.contextPath}/css/normalize.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<!-- nav begin -->
<nav class="navbar navbar-default">
    <div class="container-fluid">

        <!-- 导航栏头 -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">deck.io</a>
        </div>

        <!-- 导航内容 -->
        <ul class="nav navbar-nav">
            <li id="control-li"><a href="#">控制台</a></li>
            <li id="node-li"><a href="#">节点管理</a></li>
            <li id="cluster-li"><a href="#">集群管理</a></li>
        </ul>

        <ul class="nav navbar-nav pull-right">
            <li id="venue-login"><a href="#">root</a></li>
            <li id="manager-login"><a href="#">登出</a></li>
        </ul>
    </div>
</nav>
<!-- nav end -->

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Add Hystrix
                    </h3>
                </div>
                <div class="panel-body">
                    <form id="create-form" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-md-3 control-label">BaseDir:</label>
                            <div class="col-md-8">
                                <input id="basedir-input" type="text" class="form-control"/>
                            </div>
                        </div>
                    </form>
                    <button id="add-btn" type="button" class="col-md-2 col-md-offset-5 btn btn-default">
                        Add
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>

    $("#add-btn").on("click", function () {
        let baseDir= $("#basedir-input").val();
        console.log(JSON.stringify(baseDir));
        $.ajax({
            url: "${pageContext.request.contextPath}/addHystrix/add",
            method: "post",
            data: JSON.stringify(baseDir),
            contentType: "application/json;charset=UTF-8",
            processData: false,
            success: function (data) {
                if (data === true) {
                    alert("添加熔断成功");
                }
                else {
                    alert("添加熔断失败");
                }
            },
            error: function () {
                alert("添加熔断失败");
            }
        });
    });
</script>
</body>
</html>