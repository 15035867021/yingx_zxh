<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function () {
        //初始化jqgrid
        $("#videoTable").jqGrid({
            styleUI: "Bootstrap",//使用bootstrap主题
            url: "${path}/video/queryByPage", //指定服务器端地址
            pager: "#videoPage",
            rowNum: 2,
            rowList: [1,2, 5, 10, 20],
            viewrecords: true,    //展示总条数
            autowidth: true,
            height: "auto",
            editurl: "${path}/video/edit",//用来处理修改时url路径   String oper 参数   add 保存  edit 修改  del 删除

            datatype: "json",//指定返回数据类型
            colNames: ["id", "名称", "视频","封面", "上传时间", "描述", "类别id", "用户id"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {
                    name: "videoPath", editable: true, edittype: "file",
                    formatter: function (value, options, rowObject) {
                        return "<video controls='controls' src="+value+" width='300px' height='200px' />";
                    }
                },
                {
                    name: "coverPath",
                    formatter:function (value,options,rowObject) {
                        return "<img src="+value+" width='300px' height='200px'/>";
                    }

                },
                {name: "uploadTime"},
                {name: "brief", editable: true},
                {name: "categoryId",editable: true,edittype:'select',editoptions:{dataUrl:"${path}/category/querySecond"}},
                {name: "userId",editable: true}
            ]

        }).navGrid("#videoPage",{add: true, edit: true, del: true, search: true, refresh: true},  //对展示增删改按钮配置
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function(res){
                    alert(res.responseText);
                    $.ajaxFileUpload({
                        url:"${path}/video/updateVideo",
                        type:"post",
                        data:{"id":res.responseText},
                        fileElementId:"videoPath",
                        success:function(){
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },   //修改
            {
                closeAfterAdd: true,  //关闭面板
                reloadAfterSubmit: true,
                afterSubmit:function (data) {
                    alert(data.responseText);
                    $.ajaxFileUpload({
                        url: "${path}/video/upload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath",
                        success: function () {
                            //刷新页面
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },   //添加
            {

            }   //删除
        );

    });

</script>

<%--设置面板--%>
<div class="panel panel-info">


    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">视频信息</a>
        </li>
    </ul>

    <br>
    <div style="margin-top: 20px;">
        <%--表单--%>
        <table id="videoTable"/>

        <%--分页工具栏--%>
        <div id="videoPage"/>
    </div>
</div>
