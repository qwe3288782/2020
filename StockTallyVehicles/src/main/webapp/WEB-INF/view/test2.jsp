<%@ page import="com.yuxin.system.bean.TruckStatus" %>
<%@ page import="com.yuxin.system.bean.BillTypeBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.yuxin.system.service.TruckBillService" %>
<%@ page import="com.yuxin.system.service.Impl.TruckBillServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.yuxin.system.bean.Material" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/23
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <meta charset="utf-8">
    <title>layer iframe 示例</title>
    <link rel="stylesheet" href="layui/css/layui.css" media="all">
    <style>
        body{padding:10px; font-size:14px; background:#fff; width:95%; margin:0 auto; font-size:14px; line-height:20px; overflow:hidden;}
        p{margin-bottom:10px;}
        input{border:1px solid #999; padding:5px 10px; margin:0 10px 10px 0;}
    </style>
</head>
<body>
<%
    TruckBillService truckBillService = new TruckBillServiceImpl();
    HashMap<String,String> selectBillType = new HashMap<String, String>();
    selectBillType.put("billType","派车单");
%>
<div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>修改默认搜索条件</legend>
    </fieldset>
</div>
<div>
    <form class="layui-form">
        <div class="layui-form-item" style="font-size: 18px;padding-left: 50px">
            <label class="layui-form-label" style="width: 130px">选择刷新时间</label>
            <div class="layui-input-inline"style="width: 110px">
                <select name="refreshTime" id="refreshTime" lay-verify="required" lay-search="" >
                    <option value="5">间隔5秒</option>
                    <option value="10">间隔10秒</option>
                    <option value="30">间隔30秒</option>
                    <option value="60">间隔60秒</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="font-size: 18px;padding-left: 50px">
            <label class="layui-form-label" style="width: 130px">选择派车单类型</label>
            <div class="layui-input-inline"style="width: 190px">
                <select name="truckStatus" id="truckStatus" lay-verify="required" lay-search="">
                    <%
                        List<TruckStatus> truckStatusList = truckBillService.queryTruckStatus(new HashMap<String, String>());
                        for (int i = 0; i < truckStatusList.size(); i++) {
                            TruckStatus truckStatus = truckStatusList.get(i);
                    %>
                    <option value="<%=truckStatus.getTruckStatusName()%>"><%=truckStatus.getTruckStatusName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="font-size: 18px;padding-left: 50px">
            <label class="layui-form-label" style="width: 130px">选择车辆状态</label>
            <div class="layui-input-inline"style="width: 180px">
                <select name="billType" id="billType"  lay-search="">
                    <%
                        List<BillTypeBean> billTypeList = truckBillService.queryAllBillType(selectBillType);
                        for (int i = 0; i < billTypeList.size(); i++) {
                            BillTypeBean billTypeBean = billTypeList.get(i);
                    %>
                    <option value="<%=billTypeBean.getBillTypeName()%>"><%=billTypeBean.getBillTypeName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="margin-left: 180px">
            <button class="layui-btn layui-btn-primary" id="transmit">确定修改</button>
        </div>
    </form>
</div>
<!--<div>-->
<!--    <button class="layui-btn layui-btn-primary" id="transmit">确定修改</button>-->
<!--</div>-->

<script src="layui/layui.js"></script>
<script>
    layui.use(['form','layer'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,$ = layui.jquery;
        form.render();
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//给父页面传值
        $('#transmit').on('click', function(){
            var newRefreshTime = $('#refreshTime').val()
                ,newTruckStatus = $('#truckStatus').val()
                ,newBillType = $('#billType').val();

            parent.$('#defaultRefreshTime').val(newRefreshTime) ;
            // parent.$('#truckStatusTest').val(newTruckStatus);
            parent.$('#defaultTruckStatus').val(newTruckStatus);
            parent.$('#defaultBillType').val(newBillType);
            parent.$('#truckStatus').val(newTruckStatus);
            parent.$('#billType').val(newBillType);

            // parent.$option.eq(aRes[i].type-1).prop("selected",true);
            // parent.layer.tips('Look here', '#test', {time: 5000});
            parent.layer.close(index);
        });
    })
</script>

</body>
</html>
