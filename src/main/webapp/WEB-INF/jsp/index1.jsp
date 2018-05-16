<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Shenmiu
  Date: 08/05/2018

--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>项目创建</title>

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
                        Maven项目创建
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
                        <div class="form-group">
                            <label class="col-md-3 control-label">GroupId:</label>
                            <div class="col-md-8">
                                <input id="group-id-input" type="text" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">ArtifactId:</label>
                            <div class="col-md-8">
                                <input id="artifact-id-input" type="text" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Dependencies:</label>
                            <div class="col-md-8">
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox1" value="eurekaServer"> eurekaServer
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox2" value="eurekaDiscovery"> eurekaDiscovery
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox3" value="zuul"> zuul
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox5" value="hystrix"> hystrix
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox6" value="configServer"> configServer
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox7" value="configClient"> configClient
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="inlineCheckbox8" value="rabbitMQ"> rabbitMQ
                                </label>
                            </div>
                        </div>
                    </form>
                    <button id="create-btn" type="button" class="col-md-2 col-md-offset-5 btn btn-default">
                        创建项目
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

    $("#create-btn").on("click", function () {
        let dependencies = [];
        $("#create-form input[type=checkbox]:checked").each(function () { //由于复选框一般选中的是多个,所以可以循环输出选中的值
            dependencies.push($(this).val());
        });
        let projectInfo = {
            baseDir: $("#basedir-input").val(),
            groupId: $("#group-id-input").val(),
            artifactId: $("#artifact-id-input").val(),
            dependencies: dependencies
        };

        console.log(JSON.stringify(projectInfo));

        $.ajax({
            url: "${pageContext.request.contextPath}/createProject/create",
            method: "post",
            data: JSON.stringify(projectInfo),
            contentType: "application/json;charset=UTF-8",
            processData: false,
            success: function (data) {
                if (data === true) {
                    alert("创建项目成功");
                }
                else {
                    alert("创建项目失败");
                }
            },
            error: function () {
                alert("创建项目失败");
            }
        });
    });
</script>
</body>
</html>