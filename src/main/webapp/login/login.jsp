<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>yingx Login</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="${path}/login/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/login/assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/login/assets/css/form-elements.css">
    <link rel="stylesheet" href="${path}/login/assets/css/style.css">
    <link rel="shortcut icon" href="${path}/login/assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${path}/login/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${path}/login/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${path}/login/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${path}/login/assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/login/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="${path}/login/assets/js/jquery.backstretch.min.js"></script>
    <script src="${path}/login/assets/js/scripts.js"></script>
    <script src="${path}/login/assets/js/jquery.validate.min.js"></script>
    <script>
        //设置页面加载完执行
        $(function(){
            //给图片加点击事件
            $("#captchaImage").click(function(){
                $("#captchaImage").prop("src","${path}/admin/creatImage?yzm="+Math.random());
            });

            //给换一张加点击事件
            $("#hyz").click(function(){
                $("#captchaImage").prop("src","${path}/admin/creatImage?yzm="+Math.random());
            });

            //表单验证
            $.extend($.validator.messages, {
                required: "<span style='color:red'>这是必填字段</span>",  //提示信息
                minlength: $.validator.format("<span style='color:red'>请输入不小于 4 的数值</span>")
            });

            //提交表单
            $("#loginButtonId").click(function(){
                //表单验证
                var isOk = $("#loginForm").valid();
                if(isOk){
                    $.ajax({
                        url:"${path}/admin/login",
                        type:"post",
                        data:$("#loginForm").serialize(),  //表单序列化
                        dataType:"JSON",
                        success:function(res){
                            if(res.status==200){  //登陆成功 跳转页面
                                location.href="${path}/main/main.jsp";
                            }else{   //登陆失败  显示回显信息
                                $("#msgDiv").html("<span style='color: red'>"+res.message+"</span>");
                            }
                        }
                    })
                }

            });
        });
    </script>

</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>YINGX</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>YINGX</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showAll</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="" method="post" class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input minlength="4" type="text" name="username" placeholder="请输入用户名..." class="form-username form-control" required id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input minlength="4" type="password" name="password" placeholder="请输入密码..." class="form-password form-control" required id="form-password">
                            </div>
                            <div class="form-group">
                                <%--<label class="sr-only" for="form-code">Code</label>--%>
                                <img id="captchaImage" style="height: 48px" class="captchaImage" src="${path}/admin/creatImage">
                                <input minlength="4" style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;" required type="test" name="code" id="form-code">
                                <span id="hyz"><a href="javascript:;">换一张</a></span>
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;" id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="copyrights">Collect from <a href="http://www.cssmoban.com/" title="网站模板">网站模板</a></div>


<!-- Javascript -->

<!--[if lt IE 10]>
<script src="assets/js/placeholder.js"></script>
<![endif]-->

</body>

</html>