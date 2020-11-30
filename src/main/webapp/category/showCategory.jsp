<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>

    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/category/queryByPage",
            datatype : "json",
            rowNum : 8,
            rowList : [ 2, 8, 10, 20, 30 ],
            pager : '#catePage',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'id', '名称', '级别', '父类别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55,editable:false},
                {name : 'cateName',index : 'name',width : 100,editable:true},
                {name : 'levels',index : 'amount',width : 80,align : "right",editable:false},
                {name : 'parentId',index : 'tax',width : 80,align : "right",editable:false}
            ],
            editurl: "${path}/category/edit", //用来处理修改时url路径   String oper 参数   add 保存  edit 修改  del 删除
            subGrid : true,  //开启子表格
            // subgrid_id:是在创建表数据时创建的div标签的ID
            //row_id是该行的ID
            subGridRowExpanded : function(subgridId, rowId) {
                addSubGrid(subgridId, rowId);
            }
        });
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,addtext:"添加",edit : true,edittext:"修改",del : true,deltext:"删除"},
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
            },  //修改
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
            }, //添加
            {
                //删除成功之后触发的function,接收删除返回的提示信息,在页面做展示
                afterSubmit:function(res){
                    //设置提示信息
                    $("#message").html(res.responseJSON.message);
                    // alert(res.responseJSON.message);
                    //展示警告框
                    $("#showMsg").show();

                    //设置警告框时间
                    setTimeout(function () {
                        $("#showMsg").hide();
                    },3000);
                    return "true";
                },
                closeAfterDel: true,
                reloadAfterSubmit: true
            } //删除

        );
    }


    //开启子表格的样式
    function addSubGrid(subgridId, rowId){

        var subgridTableTd= subgridId + "Table";
        var pagerId= subgridId+"Page";


        $("#"+subgridId).html("" +
            "<table id='"+subgridTableTd+"' />" +
            "<div id='"+pagerId+"' />"
        );



        $("#" + subgridTableTd).jqGrid({
            url : "${path}/category/queryByPageSencond?parentId=" + rowId,
            datatype : "json",
            rowNum : 2,
            pager : "#"+pagerId,
            rowList : [ 2, 8, 10, 20, 30 ],
            sortname : 'num',
            sortorder : "asc",
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            editurl: "${path}/category/edit", //用来处理修改时url路径   String oper 参数   add 保存  edit 修改  del 删除
            colNames : [ 'id', '名称', '级别', '父类别id' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'name',width : 100,editable:true},
                {name : 'levels',index : 'amount',width : 80,align : "right"},
                {name : 'parentId',index : 'tax',width : 80,align : "right",editable:true,edittype:'select',editoptions:{dataUrl:"${path}/category/queryOne"},

                }
            ]
        });

        $("#" + subgridTableTd).jqGrid('navGrid',"#" + pagerId, {add : true,addtext:"添加",edit : true,edittext:"修改",del : true,deltext:"删除"},
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
            },  //修改
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
            }, //添加
            {
                //删除成功之后触发的function,接收删除返回的提示信息,在页面做展示
                afterSubmit:function(res){
                    //设置提示信息
                    $("#message").html(res.responseJSON.message);
                    // alert(res.responseJSON.message);
                    //展示警告框
                    $("#showMsg").show();

                    //设置警告框时间
                    setTimeout(function () {
                        $("#showMsg").hide();
                    },3000);
                    return "true";
                },
                closeAfterDel: true,
                reloadAfterSubmit: true
            } //删除

            );
    }


</script>


<%--设置面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>

    <%--警告框--%>
    <div id="showMsg" style="width:300px;display: none" class="alert alert-warning alert-dismissible" role="alert">
        <span id="message">dfsfsf</span>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别管理</a></li>
    </ul>

    <%--表单--%>
    <table id="cateTable" />

    <%--分页工具栏--%>
    <div id="catePage" />

</div>

<%--
    删除要有提示信息
--%>
