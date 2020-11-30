<%@page pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    $(function () {
        //初始化jqgrid
        $("#userId").jqGrid({
            styleUI: "Bootstrap",//使用bootstrap主题
            url: "${pageContext.request.contextPath}/user/queryAll", //指定服务器端地址
            pager: "#userPage",
            rowNum: 2,
            rowList: [2, 5, 10, 20],
            viewrecords: true,    //展示总条数
            autowidth: true,
            height: "auto",
            editurl: "${pageContext.request.contextPath}/user/edit", //用来处理修改时url路径   String oper 参数   add 保存  edit 修改  del 删除

            datatype: "json",//指定返回数据类型
            colNames: ["编号", "昵称", "头像", "学分", "状态", "电话", "简介", "创建时间"],
            colModel: [
                {name: "id"},
                {name: "nickName",editable:true},
                {name: "picImg",editable:true,edittype: "file",
                    formatter: function (value, options, row) {
                        return "<img src='${pageContext.request.contextPath}/" + value + "' style='height: 80px'/>"
                    }
                },
                {name: "score",editable:true},
                {name: "status",
                    formatter: function (value, options, row) {
                        if (value == "0") {
                            return "<button class='btn-success' onclick='updateStatus(\"" + row.id + "\",\"" + row.status + "\")'>正常</button>"
                        } else {
                            return "<button class='btn-danger' onclick='updateStatus(\"" + row.id + "\",\"" + row.status + "\")'>冻结</button>"
                        }
                    }
                },
                {name: "phone",editable:true},
                {name: "brief",editable:true},
                {name: "creatDate"}
            ],

        }).navGrid("#userPage",
            {add: true, edit: true, del: true, search: true, refresh: true},
            {

            },   //修改
            {
                closeAfterAdd: true,  //关闭面板
                reloadAfterSubmit: true,
                /*aftersubmit:function(res){
                    alert(res.responseText);
                    console.log(res);
                    $.ajaxFileUpload({
                        url: "/user/UploadAliyun",
                        type: "post",
                        data: {"id": res.responseText},
                        fileElementId: "picImg",
                        success: function () {
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }*/
            },   //添加
            {

            },     //删除
            {}
        );

    });

    function updateStatus(id, status) {
        if (status == "0") {
            $.post("${pageContext.request.contextPath}/user/edit", {"id": id, "status": "1", "oper": "edit"}, function (data) {
                $("#userId").trigger("reloadGrid");//刷新页面
            }, "text");
        } else {
            $.post("${pageContext.request.contextPath}/user/edit", {"id": id, "status": "0", "oper": "edit"}, function (data) {
                $("#userId").trigger("reloadGrid");//刷新页面
            }, "text");
        }
    }

</script>

<script>
    $(function(){
        //给按钮加点击事件
        $("#aliyun").click(function(){
            //先获取输入框输入的手机号
            var phone = $("#phone").val();
            // alert(phone);
            //发送ajax请求
            $.post("${pageContext.request.contextPath}/user/sendyzm",{"phoneNumbers":phone},function(res){
                console.log(res);
                if(res.status == "200"){
                    alert(res.message);
                }else{
                    alert(res.message);
                }
            },"JSON");
        });
    });
</script>

<script>
    $(function () {
        //导出
        $("#user").click(function(){
            alert("============================");
            $.get("${pageContext.request.contextPath}/user/queryAlls",function(res){
                console.log(res);
            },"JSON");
        });


        //导入
        $("#users").click(function(){
            alert("============================");
            $.get("${pageContext.request.contextPath}/user/InputStream",function(res){
                console.log(res);
            },"JSON");
        });
    });
</script>


<%--设置面板--%>
<div class="panel panel-info">


    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户管理</a>
        </li>
    </ul>

    <div>
        <div class="pull-left">
            <button class="btn btn-info" id="user">导出用户信息</button>
            <button class="btn btn-success" id="users">导入用户</button>
            <button class="btn btn-danger">测试按钮</button>
        </div>

        <div class="pull-right col-sm-5">
            <form>
                <div class="col-md-4 col-md-offset-6" style="padding: 0px;">
                    <input type="text" class="form-control" id="phone" name="phoneNumbers" placeholder="请输入手机号..." required
                           minlength="11">
                </div>
                <div class="col-md-2 pull-right" style="padding: 0px;">
                    <button type="button" id="aliyun" class="btn btn-info btn-block">发送验证码</button>
                </div>
            </form>
        </div>
    </div>
    <br>
    <div style="margin-top: 20px;">
        <%--表单--%>
        <table id="userId"/>

        <%--分页工具栏--%>
        <div id="userPage"/>
    </div>
</div>
