<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    
    <script type="text/javascript">
    	//修改管理员密码
    	function updateAdmin(){
    		var newPassword = $("#newPassword").val();
    		var testPassword = $("#testPassword").val();
    		if(newPassword != testPassword){
    			layer.msg('两次输入密码不一致');
    		}else{
    			$.post("${ctx}/admin/updateAdminPassword",{"id":"${adminInfo.id}","password":newPassword},function(data){
					if(data == "succeed"){
						x_admin_close();
					}else if(data == "fail"){
						layer.msg('修改失败');
					}
			 	}) 
    		}
    	}
    	
    </script>

</head>
<body>
    <!-- 中部开始 -->
    <div class="wrapper">
        <!-- 右侧主体开始 -->
        <div class="page-content">
          <div class="content">
            <!-- 右侧内容框架，更改从这里开始 -->
            <form class="layui-form" action="javascript:updateAdmin()">
                <div class="layui-form-item">
                    <label class="layui-form-label">
                        	<font color="red">*</font>新密码
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="newPassword" name="pass" required="" lay-verify="pass"
                        autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">
                        	<font color="red">*</font>确认密码
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="testPassword" name="pass" required="" lay-verify="pass"
                        autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">
                    </label>
                    <button class="layui-btn" key="set-mine" lay-filter="save" lay-submit>
                    	 保存          
                    </button>
                </div>
            </form>
            <!-- 右侧内容框架，更改从这里结束 -->
          </div>
        </div>
        <!-- 右侧主体结束 -->
    </div>
    <!-- 中部结束 -->
</body>
</html>