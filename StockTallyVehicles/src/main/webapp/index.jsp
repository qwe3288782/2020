<%@ page import="com.yuxin.system.bean.TruckStatus" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.yuxin.system.service.TruckBillService" %>
<%@ page import="com.yuxin.system.service.Impl.TruckBillServiceImpl" %>
<%@ page import="com.yuxin.system.bean.Material" %>
<%@ page import="com.yuxin.system.bean.BillTypeBean" %>
<%@ page import="java.io.File" %>
<%@ page import="com.yuxin.common.util.MusicPlayer" %>
<%@ page import="javazoom.jl.decoder.JavaLayerException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>当前待理货的派车车辆列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="layui/css/layui.css"  media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body >
<%
    TruckBillService truckBillService = new TruckBillServiceImpl();
    HashMap<String,String> selectBillType = new HashMap<String, String>();
    selectBillType.put("billType","派车单");
    int sessionCheck = 0;
    if (session.isNew()){
        sessionCheck = 1;
//        String audioCheck = "哈哈哈哈";
        session.setAttribute("audioCheck",0);
        session.setAttribute("sessionBillType","标准采购类派车单");
    }
    session.setAttribute("sessionCheck",sessionCheck);
    int pageCheck = Integer.parseInt(session.getAttribute("sessionCheck").toString());
    int audioCheck = Integer.parseInt(session.getAttribute("audioCheck").toString());
    System.out.println("当前放入session的值为：sessionCheck"+sessionCheck+";audioCheck:"+audioCheck);

%>
<div id="title" style="padding: 10px">
    <h1 style="margin: 0 auto;text-align: center;font-size: 3em">当前待理货的派车车辆列表</h1>
</div>
<div class="demoTable">
    <fieldset class="layui-elem-field" style="margin: 0 auto;width: 1300px;">
        <legend>过滤条件设置</legend>
        <div id="selectItem" style="padding-top: 30px;padding-bottom: 30px;margin: 0 auto">
            <form class="layui-form" id="queryForm" name="queryForm" method="post">
                <div class="layui-inline" style="font-size: 20px;padding-left: 25px">
                    <label class="layui-form-label" style="width: 130px">选择车辆状态</label>
                    <div class="layui-input-inline" style="width: 185px">
                        <select name="truckStatusSelect" id="truckStatusSelect"  lay-search="">
                            <option value="" >默认为一次过磅</option>
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
                <div class="layui-inline" style="font-size: 20px">
                    <label class="layui-form-label" style="width: 180px">选择车辆的物料名称</label>
                    <div class="layui-input-inline" style="width: 180px">
                        <select name="materialNameSelect" id="materialNameSelect"  lay-search="">
                            <option value="">直接选择或搜索</option>
                            <%
                                List<Material> materialNameList = truckBillService.queryMaterialName(new HashMap<String, String>());
                                for (int i = 0; i < materialNameList.size(); i++) {
                                    Material material = materialNameList.get(i);
                            %>
                            <option value="<%=material.getMaterialName()%>"><%=material.getMaterialName()%>
                            </option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="layui-inline" style="font-size: 20px">
                    <label class="layui-form-label" style="width: 150px">选择派车单类型</label>
                    <div class="layui-input-inline"style="width: 200px">
                        <%--                    lay-verify="required"--%>
                        <select name="billTypeSelect" id="billTypeSelect"  lay-search="">
                            <option value="">默认为采购类单据</option>
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
                <div class="layui-inline" style="padding-left:10px ">
                    <a data-type="reload" class="layui-btn" style="width: 90px" >查询单据</a>

                </div>
            </form>
            <input name="audioCheck" id="audioCheck" value="<%=audioCheck%>" style="display: none"/>
            <input name="pageCheck" id="pageCheck" value="<%=pageCheck%>" style="display: none">
        </div>
    </fieldset>
</div>
<div class="layui-collapse" lay-filter="test" style="width: 1300px;margin: 0 auto">
    <div class="layui-colla-item">
        <h2 class="layui-colla-title">默认值不是我想要的？点击修改</h2>
        <div class="layui-colla-content" style="margin-bottom: 0;">
            <form class="layui-form" action="">
                <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width:180px;padding-left: 10px ">当前默认刷新时间</label>
                                <div class="layui-input-inline">
                                    <input  class="layui-input" name="defaultRefreshTime" id="defaultRefreshTime"  />
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width:180px;padding-left: 10px ">当前默认单据类型</label>
                                <div class="layui-input-inline">
                                    <input class="layui-input" name="defaultBillType" id="defaultBillType" />
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="width:180px;padding-left: 10px ">当前默认车辆状态</label>
                                <div class="layui-input-inline">
                                    <input class="layui-input" name="defaultTruckStatus" id="defaultTruckStatus"  />
                                </div>
                            </div>
                        </div>
            </form>
            <div class="site-demo-button" id="layerDemo" style="margin: 0 auto;text-align: center">
                <button data-method="notice" class="layui-btn" id="parentIframe" style="width: 100px;margin: 0 auto">修改默认值</button>
                <%--                    <button data-method="offset" data-type="auto" class="layui-btn layui-btn-normal">确定</button>--%>
                <button onclick="changeDefaultValue()" data-type="auto" class="layui-btn layui-btn-normal" style="width: 100px">确定</button>
            </div>
        </div>
        </div>
</div>
<div style="width: 90%;margin: 0 auto;text-align: center;padding-top: 20px;padding-bottom: 100px;zoom: 120%">
    <table class="layui-hide" id="LAY_table_truckBill" lay-filter="user" style="width: 90%;margin: 0 auto;font-size: 30px " ></table>
</div>
<div id="bdtts_div_id" >

</div>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
<script src="layui/layui.js" charset="utf-8"></script>

<script>
    layui.use(['table','layer'], function(){

        var table = layui.table,
            $ = layui.$,
            layer = layui.layer;
        //默认查询条件和自定义查询条件需要合并吗？
        var truckStatusSelect = $('#truckStatusSelect'),
            materialNameSelect = $('#materialNameSelect'),
            billTypeSelect = $('#billTypeSelect'),
            //以上为自定义查询条件
            audioCheck =$('#audioCheck'),
            pageCheck = $('#pageCheck'),
            audioPlay = $('#bdtts_div_id');
        var nowTruckStatus = truckStatusSelect.val()
            ,nowBillType = billTypeSelect.val()
            ,nowMaterialName = materialNameSelect.val()
            ,nowAudioCheck = audioCheck.val()
            ,pageCheck = pageCheck.val();
        // if(nowTruckStatus == ''){
        //     // alert("获取到truckStatus的值为："+nowTruckStatus);
        //     nowTruckStatus = '1次过磅'
        // }if(nowBillType == ''){
        //     nowBillType = '标准采购类'
        // }
        // if(nowAudioCheck > 0 ){
        //     audioPlay.append('<div><audio id="tts_autio_id" autoplay="autoplay">\n' +
        //         '        <source id="tts_source_id" src="layui/music/车辆入厂提示音.mp3" type="audio/mpeg">\n' +
        //         '        <embed id="tts_embed_id" height="0" width="0" src="">\n' +
        //         '    </audio></div>')
        // }
        if(pageCheck > 0){
            //    此时页面初始化时进行绑定：将页面中的值设置为cookie默认值
            document.cookie = 'truckStatus=1次过磅';
            document.cookie = 'refreshTime=30';
            document.cookie = 'billType=标准采购类';
        }
        var billType = getCookie("billType"),
            truckStatus = getCookie("truckStatus"),
            refreshTime = getCookie("refreshTime");
        // alert()
        // alert("从cookie中获取的值为：billType:"+billType+" truckStatus:"+truckStatus+" refreshTime:"+refreshTime);
        $('#defaultTruckStatus').val(truckStatus),
            $('#defaultRefreshTime').val(refreshTime+"秒"),
            $('#defaultBillType').val(billType);
        setTimeout("location='/StockTallyVehicles/'; ", refreshTime*1000);
        // 此时cookie中默认搜索条件完成了初始化，可直接进行获取
        /*
        * 使用cookie存放多行默认值：
        *   1.页面初始化时，预先设置三项过滤条件的默认值，之后获取当前三项默认值存入cookie，在将cookie中的值取出作为查询条件；
        *   2.如何判断页面是否已经进行了初始化：
        *       通过获取pageCheck参数值，即session的校验值，当其为1，说明该页面为刚刚完成初始化；
        *   默认值的处理：
        *       1.判断是否初始化进行判定（页面加载时的进行的操作，不在HTML部分对默认查询条件进行赋值）：
        *           尚未初始化->pageCheck==1，将写定值存入cookie
        *           已经初始化->无操作
        *       2.当通过弹出层修改了默认值时的操作
        *           使用onchange()方法监听值的变化，当发生变更，放入cookie中
        *   默认值的获取：
        *       1.在数据表格渲染时获取cookie中的默认值；
        *       2.在数据表格重载时获取cookie中的默认值；
        *   如何在默认值发生改变后重新获取它的值：
        *       reload方法在表格重载时可获取默认值；
        *       表格初始加载时获取cookie中默认值的方式：
        *           table.render()方法不可写参数获取，因此有两种方式：
        *               1.事件监听：元素.on(){}；
        *               2.默认值选择框的onchange方法:或者每次获取当前默认值框后，都存入cookie，在从cookie取出绑定到默认值文本框；
        *               若以cookie中的值为准那么当前如何在表格初始化时获取cookie的值：
        *                   因为默认值修改后并非直接进行默认值的查询，需要等到下一次页面刷新时进行默认值的调用，
        *                   因此主要工作有二：
        *                           1.session创建时(pageCheck == 1)时，将5000+采购+1次过磅作为初始值放入cookie，之后步骤和pageCheck != 1时相同：取出cookie中设定的默认值；
        *                           2.在使用弹出层修改默认值时，将发生变更的默认搜索值放入cookie中；
        *   总结：页面每次加载时都获取cookie中设定的默认搜索值，在默认值发生修改后通过onchange调用方法放入cookie中去，使页面初始化时进行默认值的获取
        *
        *
        * */

        //方法级渲染
        table.render({
            elem: '#LAY_table_truckBill'
            ,url: 'truckBillAction'
            ,cellMinWidth: 80
            ,cellMaxWidth:180
            ,method:'post'
            ,cols: [[
                // {checkbox: true, fixed: true},
                {field:'wbTime1', width:350, title: '一次称重时间',sort: true}
                ,{field:'materialName', width:222, title: '物料名称'}
                ,{field:'driver', width: 160, title: '司机'}
                ,{field:'carNum', width:205, title: '车牌号', sort: true}
                ,{field:'billNo', width:315, title: '派车单编号', sort: true}
                ,{field:'billType', width:306,   title: '派车单类型'}
                ,{field:'truckStatus', width:177, title: '车辆状态',sort: true}

            ]]
            ,where:{
                truckStatus: truckStatus
                ,billType:billType
                //, materialName:nowMaterialName
                // refreshTime:refreshTime.val()
            }
            ,id: 'testReload'
            ,page: true
            ,limits: [3,5,10]  //一页选择显示3,5或10条数据
            ,limit: 10  //一页显示10条数据
            , parseData: function(res){ //将原始数据解析成 table 组件所规定的数据，res为从url中get到的数据
                var result;
                console.log(this);
                console.log(JSON.stringify(res));
                if(this.page.curr){
                    result = res.data.slice(this.limit*(this.page.curr-1),this.limit*this.page.curr);
                }
                else{
                    result=res.data.slice(0,this.limit);
                }
                if(res.msg > 0){
                    audioPlay.append('<div><audio id="tts_autio_id" autoplay="autoplay">\n' +
                        '        <source id="tts_source_id" src="layui/music/车辆入厂提示音.mp3" type="audio/mpeg">\n' +
                        '        <embed id="tts_embed_id" height="0" width="0" src="">\n' +
                        '    </audio></div>')
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": result //解析数据列表
                };
            }
        });

        //
        var active = {
            reload: function(){
                //reload执行的是表格动态重载，此时进行查询时使用的是输入的文本框中的值
                var truckStatus = $('#truckStatusSelect'),
                    materialName = $('#materialNameSelect'),
                    billType = $('#billTypeSelect');
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        truckStatus: truckStatus.val()
                        ,materialName: materialName.val()
                        ,billType: billType.val()
                    }
                }, 'data');
            }

        };

        $('.demoTable  .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        // $('#demoTest .layui-btn').on('click', function(){
        //     var type = $(this).data('type');
        //     active[type] ? active[type].call(this) : '';
        // });
    });
    layui.use(['element', 'layer'], function(){
        var element = layui.element();
        var layer = layui.layer;

        //监听折叠
        element.on('collapse(test)', function(data){
            layer.msg('展开状态：'+ data.show);
        });
    });
    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var $ = layui.jquery
            ,layer = layui.layer; //独立版的layer无需执行这一句
        //触发事件
        var active = {
            notice: function(){
                //prompt层
                layer.open({
                    type: 2,
                    area: ['800px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: './test2',
                    success: function(layer, index){
                        console.log(layer, index);
                    }
                })
            }
            ,offset: function(othis){
                var type = othis.data('type')
                    ,text = othis.text();

                layer.open({
                    type: 2,
                    area: ['800px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: './test2'
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            // $('#truckStatus').val('1次过磅');
            // alert("当前派车单状态的值为"+ $('#truckStatus').val());
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });
    function changeDefaultValue() {
        // var testAttribute = getCookie("billType");
        // alert("获取的值为"+testAttribute);
        var billType = document.getElementById("defaultBillType"),
            truckStatus = document.getElementById("defaultTruckStatus"),
            refreshTime = document.getElementById("defaultRefreshTime");
        // alert("从文本框中获取的和将要存入cookie的值为：billType:"+billType.value+" truckStatus:"+truckStatus.value+" refreshTime:"+refreshTime.value);
        document.cookie = 'truckStatus='+truckStatus.value;
        document.cookie = 'refreshTime='+refreshTime.value;
        document.cookie = 'billType='+billType.value;
        // var truckStatus2 =  getCookie("truckStatus"),
        //     refreshTime2 = getCookie("refreshTime"),
        //     billType2 = getCookie("billType");
        // alert("已经存入cookie的值为：billType:"+billType2+" truckStatus:"+truckStatus2+" refreshTime:"+refreshTime2);
        setTimeout("location='/StockTallyVehicles/'; ", refreshTime.value*1000);
        alert("修改完成！默认值将在页面自动刷新后生效!");
    //    经校验cookie存取值正确

    }
    function getCookie(name){
        var strcookie = document.cookie;//获取cookie字符串
        var arrcookie = strcookie.split("; ");//分割
        //遍历匹配
        for ( var i = 0; i < arrcookie.length; i++) {
            var arr = arrcookie[i].split("=");
            if (arr[0] == name){
                return arr[1];
            }
        }
        return "";
    }

</script>


</body>
</html>