<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function () {
    });


    //初始化jqgrid
    $("#logId").jqGrid({
        styleUI: "Bootstrap",//使用bootstrap主题
        url: "${path}/log/queryByPage", //指定服务器端地址
        pager: "#logPage",
        rowNum: 2,
        rowList: [2, 5, 10, 20],
        viewrecords: true,    //展示总条数
        autowidth: true,
        height: "auto",
        editurl: "${path}/log/edit", //用来处理修改时url路径   String oper 参数   add 保存  edit 修改  del 删除

        datatype: "json",//指定返回数据类型
        colNames: ["id", "用户名", "执行时间", "操作功能", "状态"],
        colModel: [
            {name: "id",align:'center'},
            {name: "name",align:'center'},
            {name: "times",align:'center'},
            {name: "options",align:'center'},
            {name: "status",align:'center'}
        ]

    }).navGrid("#logPage",
        {add: false, edit: false, del: true, search: false, refresh: false},
        {},  //修改
        {},    //添加
        {
            closeAfterDel: true,  //关闭面板
        },    //删除
        {}    //搜索

    );
</script>


<%--设置面板--%>
<div class="panel panel-info">


    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home" aria-controls="home" role="tab" data-toggle="tab">日志管理</a>
        </li>
    </ul>

    <br>
    <div style="margin-top: 20px;">
        <%--表单--%>
        <table id="logId"/>

        <%--分页工具栏--%>
        <div id="logPage"/>
    </div>
</div>
