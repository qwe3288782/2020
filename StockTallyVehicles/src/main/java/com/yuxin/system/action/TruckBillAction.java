package com.yuxin.system.action;

import com.yuxin.common.util.MusicPlayer;
import com.yuxin.system.bean.TruckBillBean;
import com.yuxin.system.service.Impl.TruckBillServiceImpl;
import com.yuxin.system.service.TruckBillService;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@WebServlet(name = "truckBillAction")
public class TruckBillAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入了truckBillAction");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        HttpSession hs = request.getSession();
//        PrintWriter out = response.getWriter();
//        String test2 = hs.getAttribute("audioCheck").toString();
//        System.out.println("当前传过来的sessionCheck值为："+test2);
        String test = hs.getAttribute("sessionCheck").toString();
        int msg = 0;
        System.out.println("当前传过来的sessionCheck值为："+test);
        String sessionBillType = hs.getAttribute("sessionBillType").toString();
        int sessionCheck = Integer.parseInt(test);
        String page = request.getParameter("page");
        String truckStatus = request.getParameter("truckStatus");//获取车辆状态
        String materialName = request.getParameter("materialName");//获取随车物料名称
        String billType = request.getParameter("billType");//获取单据类型
        System.out.println("当前传递的参数为：page:"+page+" truckStatus："+truckStatus+" materialName:"+materialName+" billType:"+billType+" 两个派车单类型的比较结果为："+sessionBillType.equals(billType));
        TruckBillService truckBillService = new TruckBillServiceImpl();
        HashMap<String,String> selectCondition = new HashMap<String, String>();
        selectCondition.put("truckStatus",truckStatus);
        selectCondition.put("materialName",materialName);
        selectCondition.put("billType",billType);

        int check  = truckBillService.checkTruckBillByCondition(selectCondition);
        if(check < 1){
            //表示当前查询条件在系统中没有对应值
            System.out.println("表示当前查询条件在系统中没有对应值");
        }
        List<TruckBillBean> truckBillList = truckBillService.queryTruckBillByCondition(selectCondition);
//        List<DeliveryNoticeBean> dnList = deliveryNoticeService.queryAllInfoByTime(hm1);
        JSONArray jsonArray = new JSONArray();//创建JSONArray对象
        truckBillList.forEach((item)->{
            System.out.println(item);
        });
        ArrayList<String> billNOList =  new ArrayList<>();

        /**
         * 实现新增派车单提醒的两种思路:
         *      1.通过增加首页校验参数，在首次创建session时创建存放billNO的list，从第二次查询开始进行校验,若当前派车单不存在于billNoList中，执行count++操作，最后若count>0,则说明发生了派车单新增，此时播放提示音；
         *      2.不使用校验参数和list，由于查询语句为正序排列，即最后的派车单就是最新派车单，在上一次查询最后将最新派车单的一次过磅时间保存，若本次查询的最后一条数据中一次过磅时间大于上次一次过磅时间，则说明有新车进行了一次过磅
         * */

        /**
         * 1.当前查询的最后一次过磅时间放在哪里？
         * 2.上一次的最后一次过磅时间放在哪里？
         *      若是session创建后的初次查询，则将本次查询的最后一条数据的过磅时间放入session中；
         *      而若非session创建后的初次查询，则需要先进行session中过磅时间的取数，在将当前取到的过磅时间同session中存放的过磅时间进行比较，
         *      若当前时间在session中过磅时间之后，则进行session存放的过磅时间的刷新，同时执行提示音操作
         * */


        int truckBillListSize = truckBillList.size();
        if(truckBillListSize > 0 ){
            for (int i = 0; i < truckBillListSize; i++) {
            TruckBillBean truckBillBean = truckBillList.get(i);
            JSONObject jsonObject = new JSONObject();//为查询的每一个bean创建一个json,并将其返回到JSONArray中
            jsonObject.put("billNo",truckBillBean.getBillNo());
            //若当前为首次创建session，则将第一次查询的单号放入list
            if(sessionCheck > 0 || !sessionBillType.equals(billType) ){
                System.out.println("首次创建"+billType+"单据的查询，sessionCheck的值为:"+sessionCheck+" 当前两个billType的比较结果："+sessionBillType.equals(billType));
                billNOList.add(i,truckBillBean.getBillNo());
                //此处为两种方法的分割
                if(i == truckBillListSize - 1){
                    //若当前已经进入最后一条数据的取数，则取其过磅日期作为最新过磅日期为校验值

                    String thisWBTime1 = truckBillBean.getWbTime1();
                    System.out.println("当前为本次"+billType+"单据首次查询的最后一条信息，即最新过磅单据，此时记录的最新过磅时间为："+thisWBTime1+"此时session中billType为："+sessionBillType+" 实际操作的billType为："+billType);
//                    if(checkEmpty(thisWBTime1)){
                        hs.setAttribute("WBTime1",thisWBTime1);
//                    }
                }
                hs.setAttribute("sessionBillType",billType);

            }else{
                System.out.println("非首次创建"+billType+"单据的查询，sessionCheck的值为:"+sessionCheck);
                String newBillNo = truckBillBean.getBillNo();
                //此处为两种方法的分割
                if(i == truckBillListSize - 1){
                    //若当前已经进入最后一条数据的取数，则取其过磅日期作为最新过磅日期为校验值
                    System.out.println("当前为本次"+billType+"单据查询的最后一条信息，即最新过磅单据");
                    String thisWBTime1 = truckBillBean.getWbTime1();
                    String lastWBTime1 = hs.getAttribute("WBTime1").toString();
//                    Date date = thisWBTime1;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    boolean checkTime = false;
                    try {
                        Date thisTime = simpleDateFormat.parse(thisWBTime1);
                        Date lastTime = simpleDateFormat.parse(lastWBTime1);
                        checkTime = thisTime.after(lastTime);//thisTime晚于lastTime则返回true
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(checkTime){
                          hs.setAttribute("audioCheck",99);
                          msg = 99;
                        hs.setAttribute("WBTime1",thisWBTime1);

                    }else {
                        hs.setAttribute("audioCheck",0);

                    }
                    System.out.println("当前audioCheck的值为："+hs.getAttribute("audioCheck").toString());
                }
            }
            jsonObject.put("wbTime1",truckBillBean.getWbTime1());
            jsonObject.put("materialName",truckBillBean.getMaterialName());
            jsonObject.put("driver",truckBillBean.getDriver());
            jsonObject.put("billType",truckBillBean.getBillType());
            jsonObject.put("carNum",truckBillBean.getCarNum());
            jsonObject.put("truckStatus",truckBillBean.getCarStatus());

            jsonArray.add(jsonObject);
        }
        }

        JSONObject main = new JSONObject();
        main.put("code",0);
        main.put("msg",msg);
        main.put("count",truckBillList.size());
        main.put("data",jsonArray);
//        JSONArray array= JSONArray.parseArray(JSON.toJSONString(truckBillList));
        request.setAttribute("truckBillList", truckBillList);
        request.setAttribute("truckStatus",truckStatus);
        request.setAttribute("materialName",materialName);
        request.setAttribute("billType",billType);

        String mainToString = main.toString();
        String jsonArrayToString = jsonArray.toString();
//        String testJsonToString = new JSONArray().toString();
        System.out.println("此时的jsonArray的数据为："+jsonArrayToString);
        System.out.println("-----");
        response.setCharacterEncoding("UTF-8");//设置字符编码
        PrintWriter out = response.getWriter();
        out.print(mainToString);
        out.flush();
        out.close();
        return;
    }
    public boolean checkEmpty(String parameter){
        boolean check  = false;
        if(parameter == null || parameter.isEmpty() || "".equals(parameter)) {
            check = true;
        }
        return check;
    }
}
