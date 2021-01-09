package com.yuxin.system.action;

import com.yuxin.system.bean.TruckBillBean;
import com.yuxin.system.service.Impl.TruckBillServiceImpl;
import com.yuxin.system.service.TruckBillService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "truckBillAction")
public class testAction extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TruckBillService truckBillService = new TruckBillServiceImpl();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public testAction() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
//        String uname = request.getParameter("uname");//获取到输入的用户名
        System.out.println("ajax进入了testAction");
        String truckStatus = request.getParameter("truckStatus");//获取车辆状态
        String materialName = request.getParameter("materialName");//获取随车物料名称
        int page = Integer.parseInt(request.getParameter("page"));//获取页面页码
        int limit = Integer.parseInt(request.getParameter("limit"));//获取一页显示数量
        System.out.println("来试试能不能显示^_^");
        System.out.println("前台传输过来的page = "+page+",limit = "+limit);
        String billType = request.getParameter("billType");//获取单据类型
        HashMap<String,String> selectCondition = new HashMap<String, String>();
        selectCondition.put("truckStatus",truckStatus);
        selectCondition.put("materialName",materialName);
        selectCondition.put("billType",billType);

//        List<TruckBillBean> truckBillList = truckBillService.queryTruckBillByCondition(selectCondition);
        List<TruckBillBean> truckBillList = truckBillService.queryTruckBillByCondition(selectCondition);
//        List<DeliveryNoticeBean> dnList = deliveryNoticeService.queryAllInfoByTime(hm1);
        JSONArray jsonArray = new JSONArray();
        truckBillList.forEach((item)->{
            System.out.println(item);
        });
//        jsonArray.add("code":0);
        for (int i = 0; i < truckBillList.size(); i++) {
            TruckBillBean truckBillBean = truckBillList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billNo",truckBillBean.getBillNo());
            jsonObject.put("wbTime1",truckBillBean.getWbTime1());
            jsonObject.put("materialName",truckBillBean.getMaterialName());
            jsonObject.put("billType",truckBillBean.getBillType());
            jsonObject.put("driver",truckBillBean.getDriver());
            jsonObject.put("carNum",truckBillBean.getCarNum());
            jsonArray.add(jsonObject);
        }
        JSONObject main = new JSONObject();
        main.put("code",0);
        main.put("msg","");
        main.put("count",truckBillList.size());
        main.put("data",jsonArray);
        String mainToString = main.toString();
        response.setCharacterEncoding("UTF-8");//设置字符编码
        PrintWriter out = response.getWriter();
        out.print(mainToString);
        out.flush();
        out.close();//关闭资源
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
//        String uname = request.getParameter("uname");//获取到输入的用户名
        System.out.println("ajax进入了testAction");
        String truckStatus = request.getParameter("truckStatus");//获取车辆状态
        String materialName = request.getParameter("materialName");//获取随车物料名称
//        int page = Integer.parseInt(request.getParameter("page"));//获取页面页码
//        int limit = Integer.parseInt(request.getParameter("limit"));//获取一页显示数量
//        System.out.println("来试试能不能显示^_^");
//        System.out.println("前台传输过来的page = "+page+",limit = "+limit);
        String billType = request.getParameter("billType");//获取单据类型
        HashMap<String,Object> selectCondition = new HashMap<String, Object>();
        selectCondition.put("truckStatus",truckStatus);
        selectCondition.put("materialName",materialName);
        selectCondition.put("billType",billType);
//        selectCondition.put("page",page);
//        selectCondition.put("limit",limit);

        List<TruckBillBean> truckBillList = truckBillService.queryTruckBillByCondition(selectCondition);
//        List<TruckBillBean> truckBillList = truckBillService.queryTruckBillByPageCount(selectCondition);
//        List<DeliveryNoticeBean> dnList = deliveryNoticeService.queryAllInfoByTime(hm1);
        JSONArray jsonArray = new JSONArray();
        truckBillList.forEach((item)->{
            System.out.println(item);
        });
//        jsonArray.add("code":0);
        for (int i = 0; i < truckBillList.size(); i++) {
            TruckBillBean truckBillBean = truckBillList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billNo",truckBillBean.getBillNo());
            jsonObject.put("wbTime1",truckBillBean.getWbTime1());
            jsonObject.put("materialName",truckBillBean.getMaterialName());
            jsonObject.put("billType",truckBillBean.getBillType());
            jsonObject.put("driver",truckBillBean.getDriver());
            jsonObject.put("carNum",truckBillBean.getCarNum());
            jsonArray.add(jsonObject);
        }
        JSONObject main = new JSONObject();
        main.put("code",0);
        main.put("msg","");
        main.put("count",truckBillList.size());
        main.put("data",jsonArray);
        String mainToString = main.toString();
        response.setCharacterEncoding("UTF-8");//设置字符编码
        PrintWriter out = response.getWriter();
        out.print(mainToString);
        out.flush();
        out.close();//关闭资源
    }

}
