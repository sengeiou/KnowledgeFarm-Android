<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/2 0002
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>知识农场后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/css/font.css">
    <link rel="stylesheet" href="${ctx}/css/xadmin.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/Swiper/3.4.2/css/swiper.min.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/Swiper/3.4.2/js/swiper.jquery.min.js"></script>
    <script src="${ctx}/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/js/xadmin.js"></script>

    <style>
        .page{
            margin-right:25px;
        }
    </style>

    <script>
        //初始化左侧菜单（作物管理）
        window.onload = function(){
            $("#initPetManager").attr("class","sub-menu opened");
            $("#initPetManager2").attr("class","current");
        }

        //恢复单个作物信息
        function recoveryOnePet(id){
            layer.confirm('确认要恢复吗？',function(index){
                $.post("${ctx}/admin/pet/recoveryOnePet",{"id":id},function(data){
                    if(data == "succeed"){
                        window.location.href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.currentPageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}";
                    }else if(data == "fail"){
                        layer.msg('恢复失败');
                    }
                })
            });
        }

        //恢复批量作物信息
        function recoveryMultiPet() {
            var arrRecovery = document.getElementsByName("checkBox");
            var recoveryStr="";
            for(i in arrRecovery){
                if(arrRecovery[i].checked){
                    recoveryStr = recoveryStr + arrRecovery[i].value + ",";
                }
            }
            layer.confirm('确认要批量恢复吗？',function(index){
                if(recoveryStr != ""){
                    $.post("${ctx}/admin/pet/recoveryMultiPet",{"recoveryStr":recoveryStr},function(data){
                        if(data == "succeed"){
                            window.location.href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.currentPageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}";
                        }else if(data == "fail"){
                            layer.msg('恢复失败');
                        }
                    })
                }else{
                    layer.msg('恢复不能为空');
                }
            });
        }

        //彻底删除作物信息
        function deleteThoroughPet(id){
            layer.confirm('彻底删除无法恢复，确认要删除数据吗？',function(index){
                $.post("${ctx}/admin/pet/deleteThoroughPet",{"id":id},function(data){
                    if(data == "succeed"){
                        window.location.href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.currentPageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}";
                    }else if(data == "fail"){
                        layer.msg('删除失败');
                    }
                })
            });
        }

        function changePetForm1(id,count,picture1,picture2){
            switch (count) {
                case 1:
                    var id1 = "#" + id + "1";
                    var picture = $(id1).attr('src');
                    if(picture.endsWith(".gif")){
                        if(picture1.startsWith("http")){
                            $(id1).attr('src', picture1);
                        }else{
                            $(id1).attr('src', "${ctx}/photo/"+picture1);
                        }
                    }else{
                        if(picture2.startsWith("http")){
                            $(id1).attr('src', picture2);
                        }else{
                            $(id1).attr('src', "${ctx}/photo/"+picture2);
                        }
                    }
                    break;
                case 2:
                    var id2 = "#" + id + "2";
                    var picture = $(id2).attr('src');
                    if(picture.endsWith(".gif")){
                        if(picture1.startsWith("http")){
                            $(id2).attr('src', picture1);
                        }else{
                            $(id2).attr('src', "${ctx}/photo/"+picture1);
                        }
                    }else{
                        if(picture2.startsWith("http")){
                            $(id2).attr('src', picture2);
                        }else{
                            $(id2).attr('src', "${ctx}/photo/"+picture2);
                        }
                    }
                    break;
                case 3:
                    var id3 = "#" + id + "3";
                    var picture = $(id3).attr('src');
                    if(picture.endsWith(".gif")){
                        if(picture1.startsWith("http")){
                            $(id3).attr('src', picture1);
                        }else{
                            $(id3).attr('src', "${ctx}/photo/"+picture1);
                        }
                    }else{
                        if(picture2.startsWith("http")){
                            $(id3).attr('src', picture2);
                        }else{
                            $(id3).attr('src', "${ctx}/photo/"+picture2);
                        }
                    }
                    break;
            }
        }

        function changePetForm2(id,picture1,picture2,picture3,picture4,picture5,picture6) {
            var id1 = "#" + id + "1";
            var id2 = "#" + id + "2";
            var id3 = "#" + id + "3";

            var picture01 = $(id1).attr('src');
            var picture02 = $(id2).attr('src');
            var picture03 = $(id3).attr('src');

            if(picture01.endsWith(".gif") || picture02.endsWith(".gif") || picture03.endsWith(".gif")){
                if(picture1.startsWith("http")){
                    $(id1).attr('src', picture1);
                }else{
                    $(id1).attr('src', "${ctx}/photo/"+picture1);
                }
                if(picture2.startsWith("http")){
                    $(id2).attr('src', picture2);
                }else{
                    $(id2).attr('src', "${ctx}/photo/"+picture2);
                }
                if(picture3.startsWith("http")){
                    $(id3).attr('src', picture3);
                }else{
                    $(id3).attr('src', "${ctx}/photo/"+picture3);
                }
            }else{
                if(picture4.startsWith("http")){
                    $(id1).attr('src', picture4);
                }else{
                    $(id1).attr('src', "${ctx}/photo/"+picture4);
                }
                if(picture5.startsWith("http")){
                    $(id2).attr('src', picture5);
                }else{
                    $(id2).attr('src', "${ctx}/photo/"+picture5);
                }
                if(picture6.startsWith("http")){
                    $(id3).attr('src', picture6);
                }else{
                    $(id3).attr('src', "${ctx}/photo/"+picture6);
                }
                <%--$(id1).attr('src', "${ctx}/photo/"+picture4);--%>
                <%--$(id2).attr('src', "${ctx}/photo/"+picture5);--%>
                <%--$(id3).attr('src', "${ctx}/photo/"+picture6);--%>
            }
        }
    </script>

</head>
<body>
<!-- 顶部开始 -->
<%--    	<%@ include file="/layout/header.jsp"%>--%>
<div class="container">
    <div class="logo"><a href="${ctx}/admin/toIndex">知识农场后台管理系统</a></div>
    <div class="open-nav"><i class="iconfont">&#xe699;</i></div>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">${admin.account}</a>
            <dl class="layui-nav-child">
                <dd><a href="${ctx}/admin/logout">退出</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item"><a href="/"></a></li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<div class="wrapper">
    <!-- 左侧菜单开始 -->
    <%--        	<%@ include file="/layout/menuLeft.jsp"%>--%>
    <div class="left-nav">
        <div id="side-nav">
            <ul id="nav">
                <li class="list" current>
                    <a href="${ctx}/admin/toIndex">
                        <i class="iconfont">&#xe761;</i>
                        欢迎页面
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                </li>
                <li class="list">
                    <a href="javascript:;">
                        <i class="iconfont">&#xe70b;</i>
                        用户管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initUserManager" class="sub-menu">
                        <li id="initUserManager1">
                            <a href="${ctx}/admin/user/findUserPage?exist=1">
                                <i class="iconfont">&#xe6a7;</i>
                                用户列表
                            </a>
                        </li>
                        <li id="initUserManager2">
                            <a href="${ctx}/admin/user/findUserPage?exist=0">
                                <i class="iconfont">&#xe6a7;</i>
                                用户删除
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        作物管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initCropManager" class="sub-menu">
                        <li id="initCropManager1">
                            <a href="${ctx}/admin/crop/findCropPage?exist=1">
                                <i class="iconfont">&#xe6a7;</i>
                                作物列表
                            </a>
                        </li>
                        <li id="initCropManager2">
                            <a href="${ctx}/admin/crop/findCropPage?exist=0">
                                <i class="iconfont">&#xe6a7;</i>
                                作物删除
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        宠物管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initPetManager" class="sub-menu">
                        <li id="initPetManager1">
                            <a href="${ctx}/admin/pet/findPetPage?exist=1">
                                <i class="iconfont">&#xe6a7;</i>
                                宠物列表
                            </a>
                        </li>
                        <li id="initPetManager2">
                            <a href="${ctx}/admin/pet/findPetPage?exist=0">
                                <i class="iconfont">&#xe6a7;</i>
                                宠物删除
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        宠物道具管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initPetUtilManager" class="sub-menu">
                        <li id="initPetUtilManager1">
                            <a href="${ctx}/admin/petUtil/findPetUtilPage?exist=1">
                                <i class="iconfont">&#xe6a7;</i>
                                宠物道具列表
                            </a>
                        </li>
                        <li id="initPetUtilManager2">
                            <a href="${ctx}/admin/petUtil/findPetUtilPage?exist=0">
                                <i class="iconfont">&#xe6a7;</i>
                                宠物道具删除
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list">
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        用户宠物管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initUserPetHouseManager" class="sub-menu">
                        <li id="initUserPetHouseManager1">
                            <a href="${ctx}/admin/user_pet_house/findUserPetHousePage">
                                <i class="iconfont">&#xe6a7;</i>
                                用户宠物列表
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list">
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        土地管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initUserLandManager" class="sub-menu">
                        <li id="initUserLandManager1">
                            <a href="${ctx}/admin/land/findPageLand">
                                <i class="iconfont">&#xe6a7;</i>
                                土地列表
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        通知管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="sendNotificationManager" class="sub-menu">
                        <li id="sendPushManager">
                            <a href="${ctx}/admin/notification/toNotification">
                                <i class="iconfont">&#xe6a7;</i>
                                发送通知
                            </a>
                        </li>
                        <li id="sendCustomMessageManager">
                            <a href="${ctx}/admin/notification/toNotificationCustomMessage">
                                <i class="iconfont">&#xe6a7;</i>
                                自定义消息
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        题目管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initQuestionManager" class="sub-menu">
                        <li id="initQuestionManager1">
                            <a href="${ctx}/admin/question/findAllQuestion?questionTypeId=1">
                                <i class="iconfont">&#xe6a7;</i>
                                单选题
                            </a>
                        </li>
                        <li id="initQuestionManager2">
                            <a href="${ctx}/admin/question/findAllQuestion?questionTypeId=2">
                                <i class="iconfont">&#xe6a7;</i>
                                填空题
                            </a>
                        </li>
                        <li id="initQuestionManager3">
                            <a href="${ctx}/admin/question/findAllQuestion?questionTypeId=3">
                                <i class="iconfont">&#xe6a7;</i>
                                判断题
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list" >
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        管理员管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initAdminManager" class="sub-menu">
                        <li id="initAdminManager1">
                            <a href="${ctx}/admin/findAdminPage?exist=1">
                                <i class="iconfont">&#xe6a7;</i>
                                管理员列表
                            </a>
                        </li>
                        <li id="initAdminManager2">
                            <a href="${ctx}/admin/findAdminPage?exist=0">
                                <i class="iconfont">&#xe6a7;</i>
                                管理员删除
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="list">
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6a3;</i>
                        版本更新管理
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul id="initVersionManager" class="sub-menu">
                        <li id="initVersionManager1">
                            <a href="${ctx}/admin/version/findVersionPage">
                                <i class="iconfont">&#xe6a7;</i>
                                版本列表
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="content">
            <!-- 右侧内容框架，更改从这里开始 -->
            <form class="layui-form xbs" action="${ctx}/admin/pet/findPetPage">
                <div class="layui-form-pane" style="text-align: center;">
                    <div class="layui-form-item" style="display: inline-block;">
                        <div class="layui-input-inline">
                            <input type="text" name="name" placeholder="请输入宠物名称" autocomplete="off" class="layui-input" value="${param.name}">
                            <input type="hidden" name="exist" value="0"/>
                        </div>
                        <div class="layui-input-inline" style="width:80px">
                            <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
                        </div>
                    </div>
                </div>
            </form>
            <xblock>
                <button class="layui-btn layui-btn-danger" onclick="recoveryMultiPet()">
                    <i class="layui-icon">&#xe640;</i>批量恢复
                </button>
                <span class="x-right" style="line-height:40px">共有数据：${petPage.totalCount} 条</span>
            </xblock>
            <table class="layui-table">
                <thead >
                <tr>
                    <th></th>
                    <th style="text-align:center;">宠物ID</th>
                    <th style="text-align:center;">名称</th>
                    <th style="text-align:center;">描述</th>
                    <th style="text-align:center;">img1</th>
                    <th style="text-align:center;">img2</th>
                    <th style="text-align:center;">img3</th>
                    <th style="text-align:center;">价格</th>
                    <th style="text-align:center;">生命值</th>
                    <th style="text-align:center;">智力值</th>
                    <th style="text-align:center;">体力值</th>
                    <th style="text-align:center;">收获1</th>
                    <th style="text-align:center;">收获2</th>
                    <th style="text-align:center;">收获3</th>
                    <th style="text-align:center;">生长1</th>
                    <th style="text-align:center;">生长2</th>
                    <th style="text-align:center;">生长3</th>
                    <th style="text-align:center;">切换</th>
                    <th style="text-align:center;">状态</th>
                    <th style="text-align:center;">操作</th>
                </tr>
                </thead>
                <tbody align="center">
                <c:forEach var="petPage" items="${petPage.list}">
                    <tr>
                        <td><input type="checkbox" value="${petPage.id}" name="checkBox"></td>
                        <td>${petPage.id}</td>
                        <td>${petPage.name}</td>
                        <td>${petPage.description}</td>
                        <td>
                            <div style="width:50px;height:50px;border-radius:100%;overflow: hidden;">
                                <c:if test="${fn:startsWith(petPage.img1, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("1")}" style="width:50px;height:50px;" src="${petPage.img1}" onclick="changePetForm1(${petPage.id},1,'${petPage.img1}','${petPage.gif1}')"/>
                                </c:if>
                                <c:if test="${not fn:startsWith(petPage.img1, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("1")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img1}" onclick="changePetForm1(${petPage.id},1,'${petPage.img1}','${petPage.gif1}')"/>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <div style="width:50px;height:50px;border-radius:100%;overflow: hidden;">
                                <c:if test="${fn:startsWith(petPage.img2, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("2")}" style="width:50px;height:50px;" src="${petPage.img2}" onclick="changePetForm1(${petPage.id},2,'${petPage.img2}','${petPage.gif2}')"/>
                                </c:if>
                                <c:if test="${not fn:startsWith(petPage.img2, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("2")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img2}" onclick="changePetForm1(${petPage.id},2,'${petPage.img2}','${petPage.gif2}')"/>
                                </c:if>
                            </div>
                        </td>
                        <td>
                            <div style="width:50px;height:50px;border-radius:100%;overflow: hidden;">
                                <c:if test="${fn:startsWith(petPage.img3, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("3")}" style="width:50px;height:50px;" src="${petPage.img3}" onclick="changePetForm1(${petPage.id},3,'${petPage.img3}','${petPage.gif3}')"/>
                                </c:if>
                                <c:if test="${not fn:startsWith(petPage.img3, 'http')}">
                                    <img id="${"".concat(petPage.id).concat("3")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img3}" onclick="changePetForm1(${petPage.id},3,'${petPage.img3}','${petPage.gif3}')"/>
                                </c:if>
                            </div>
                        </td>
<%--                        <td><img id="${"".concat(petPage.id).concat("1")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img1}" onclick="changePetForm1(${petPage.id},1,'${petPage.img1}','${petPage.gif1}')"/></td>--%>
<%--                        <td><img id="${"".concat(petPage.id).concat("2")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img2}" onclick="changePetForm1(${petPage.id},2,'${petPage.img2}','${petPage.gif2}')"/></td>--%>
<%--                        <td><img id="${"".concat(petPage.id).concat("3")}" style="width:50px;height:50px;" src="${ctx}/photo/${petPage.img3}" onclick="changePetForm1(${petPage.id},3,'${petPage.img3}','${petPage.gif3}')"/></td>--%>
                        <td>${petPage.price}金币</td>
                        <td>${petPage.life}</td>
                        <td>${petPage.intelligence}</td>
                        <td>${petPage.physical}</td>
                        <td>${petPage.petFunction.harvestHour1}</td>
                        <td>${petPage.petFunction.harvestHour2}</td>
                        <td>${petPage.petFunction.harvestHour3}</td>
                        <td>${petPage.petFunction.growHour1}</td>
                        <td>${petPage.petFunction.growHour2}</td>
                        <td>${petPage.petFunction.growHour3}</td>
                        <td class="td-status">
                            <span class="layui-btn layui-btn-normal layui-btn-mini" onclick="changePetForm2(${petPage.id},'${petPage.img1}','${petPage.img2}','${petPage.img3}','${petPage.gif1}','${petPage.gif2}','${petPage.gif3}')">切换</span>
                        </td>
                        <td class="td-status">
                            <span class="layui-btn layui-btn-danger layui-btn-mini">已删除</span>
                        </td>
                        <td class="td-manage" align="center">
                            <a style="text-decoration:none" onclick="recoveryOnePet(${petPage.id})" href="javascript:;" title="恢复">
                                <i class="layui-icon">&#xe618;</i>
                            </a>
                            <a title="彻底删除" href="javascript:;" onclick="deleteThoroughPet(${petPage.id})" style="text-decoration:none">
                                <i class="layui-icon">&#xe640;</i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 右侧内容框架，更改从这里结束 -->
        </div>
        <!-- 分页处理开始 -->
        <div align="center">
            <a  class="page" style="margin-left:25px;" href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=1&&pageSize=${petPage.pageSize}&&exist=${param.exist}">首页</a>
            <a  class="page" href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.prePageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}">上一页</a>
            <a  class="page" href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.nextPageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}">下一页</a>
            <a  class="page" href="${ctx}/admin/pet/findPetPage?name=${param.name}&&pageNumber=${petPage.totalPageNum}&&pageSize=${petPage.pageSize}&&exist=${param.exist}">末页</a>
        </div>
        <div align="center" style="margin-top:20px;">
            <span style="margin-right:10px;">${petPage.currentPageNum}</span>
            <span>/</span>
            <span style="margin-left:10px;">${petPage.totalPageNum}</span>
        </div>
        <!-- 分页处理结束 -->
    </div>
    <!-- 右侧主体结束 -->
</div>
<!-- 中部结束 -->
<!-- 底部开始 -->
<!-- 底部结束 -->
<!-- 背景切换开始 -->
<%--    	<%@ include file="/layout/background.jsp"%>--%>
<div class="bg-changer">
    <div class="swiper-container changer-list">
        <div class="swiper-wrapper">
            <<div class="swiper-slide"><img class="item" src="${ctx}/images/a.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/b.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/c.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/d.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/e.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/f.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/g.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/h.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/i.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/j.jpg" alt=""></div>
            <div class="swiper-slide"><img class="item" src="${ctx}/images/k.jpg" alt=""></div>
            <div class="swiper-slide"><span class="reset">恢复默认</span></div>
        </div>
    </div>
    <div class="bg-out"></div>
    <div id="changer-set"><i class="iconfont">&#xe696;</i></div>
</div>
<!-- 背景切换结束 -->
</body>
</html>
</html>
