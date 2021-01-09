package com.yuxin.system.dao.Impl;

import com.yuxin.common.util.ConnectionPool;
import com.yuxin.system.bean.BillTypeBean;
import com.yuxin.system.bean.Material;
import com.yuxin.system.bean.TruckBillBean;
import com.yuxin.system.bean.TruckStatus;
import com.yuxin.system.dao.TruckBillDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TruckBillDAOImpl implements TruckBillDAO {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @Override
    public List queryTruckBillOnFirstWeigh(HashMap<String, String> queryInfoMap) {
        List<TruckBillBean> truckBillBeans = new ArrayList<>();
        conn = ConnectionPool.getConn();
//        String sql = "SELECT FBIllNO ,T_BAS_BILLTYPE_L.FNAME FBILLTYPE,T_BD_MATERIAL_L.Fname FMATERIALNAME,FDRIVER ,FCARNUM ,FPCDLZ,FWBQTY1,FWBTIME1,FQTY,StockInfo.FStrockName FSTOCKNAME FROM T_SD_ZNTRUCK LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID \n" +
//                "LEFT JOIN T_BAS_BILLTYPE_L ON T_BAS_BILLTYPE_L.FBILLTYPEID = T_SD_ZNTRUCK.FBILLTYPEID  LEFT JOIN ( SELECT T_ZN_PCDLH.Fentryid FENTRYID, T_BD_STOCK_L.FNAME FStrockName  FROM T_ZN_PCDLH, T_BD_STOCK_L  WHERE T_ZN_PCDLH.FSUBSTOCKID = T_BD_STOCK_L.FSTOCKID ) StockInfo on StockInfo.FentryId = T_SD_ZNTRUCKENTRY.fentryid WHERE FPCDLZ = '566a2777be2a0d'  AND FWBTIME1 > ?  ORDER BY FWBTIME1 DESC";
        /**
         * sql语句：
         *  SELECT FBIllNO 派车单编号,FWBTIME1 一次称重时间,T_BD_MATERIAL_L.Fname 物料名称,T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE 车辆状态
         * 	,FDRIVER 司机,FCARNUM 车牌号,FWBQTY1 一次称重,FQTY 计划数量
         * FROM
         * 	T_SD_ZNTRUCK
         * 	LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID
         * 	LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID
         * 	left join T_BAS_ASSISTANTDATAENTRY_L on T_SD_ZNTRUCK.FPCDLZ = T_BAS_ASSISTANTDATAENTRY_L.FENTRYID
         * WHERE
         * 	FPCDLZ = '566a2777be2a0d' and FBILLTYPEID = '566938b055fdfd' and FCLOSESTATUS = 'A'
         * ORDER BY FWBTIME1 ASC
         * */

        String sql = "SELECT FBIllNO ,FWBTIME1 ,T_BD_MATERIAL_L.Fname FMATERIALNAME,FDRIVER ,FCARNUM \n" +
                "FROM T_SD_ZNTRUCK LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID\n" +
                "WHERE FPCDLZ = '566a2777be2a0d' and FBILLTYPEID in (select FBILLTYPEID from T_BAS_BILLTYPE_L WHERE FNAME like ?) and FCLOSESTATUS = 'A' ORDER BY FWBTIME1 asc";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+(String) queryInfoMap.get("billType")+"%");
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TruckBillBean truckBillBean = new TruckBillBean();
                truckBillBean.setBillNo(rs.getString("FBILLNO"));//单据编号
//                truckBillBean.setBillType(rs.getString("FBILLTYPE"));//单据类型
                truckBillBean.setMaterialName(rs.getString("FMATERIALNAME"));//物料名称
                truckBillBean.setDriver(rs.getString("FDRIVER"));//司机
                truckBillBean.setCarNum(rs.getString("FCARNUM"));//车牌号
//                truckBillBean.setCarStatus(rs.getString("FPCDLZ"));//车辆状态
//                truckBillBean.setWbQTY1(rs.getDouble("FWBQTY1"));//一次称重
                String wbTime = rs.getString("FWBTime1");
                wbTime = wbTime.substring(5,16);
                truckBillBean.setWbTime1(wbTime);//一次称重时间
//                truckBillBean.setQTY(rs.getDouble("FQTY"));//计划数量
//                truckBillBean.setStockName(rs.getString("FSTOCKNAME"));//仓库名称
                truckBillBeans.add(truckBillBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return truckBillBeans;
    }

    @Override
    public List queryAllBillType(HashMap<String, String> queryInfoMap) {
        List<BillTypeBean> billTypeBeans = new ArrayList<>();
        conn = ConnectionPool.getConn();
        String sql = "select FBILLTYPEID,FNAME FROM T_BAS_BILLTYPE_L WHERE FNAME like ? and FBILLTYPEID in(SELECT FBILLTYPEID from T_SD_ZNTRUCK)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+(String) queryInfoMap.get("billType")+"%");
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BillTypeBean billTypeBean = new BillTypeBean();
                billTypeBean.setBillTypeId(rs.getString("FBILLTYPEID"));//单据编号
                billTypeBean.setBillTypeName(rs.getString("FNAME"));//单据名称
                billTypeBeans.add(billTypeBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return billTypeBeans;
    }

    @Override
    public List queryMaterialName(HashMap<String, String> queryInfoMap) {
        List<Material> materials = new ArrayList<>();
        conn = ConnectionPool.getConn();
        String sql1 = "SELECT FMATERIALID,FNAME,FSPECIFICATION FROM T_BD_MATERIAL_L ";
        String sql = "SELECT DISTINCT FNAME from T_BD_MATERIAL_L WHERE FMATERIALID in (select FMATERIALID from T_SD_ZNTRUCK WHERE FPCDLZ like '566a2777be2a0d') and FNAME not like '%禁用%'";
        try {
            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1,"%"+(String) queryInfoMap.get("billType")+"%");
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Material material = new Material();
//                material.setMaterialId(rs.getInt("FMATERIALID"));//物料编码
                material.setMaterialName(rs.getString("FNAME"));//物料名称
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return materials;
    }

    @Override
    public List queryTruckStatus(HashMap<String, String> queryInfoMap) {
        List<TruckStatus> truckStatuses = new ArrayList<>();
        conn = ConnectionPool.getConn();
        String sql = "select FENTRYID,FDATAVALUE from T_BAS_ASSISTANTDATAENTRY_L WHERE FENTRYID in (SELECT FPCDLZ from T_SD_ZNTRUCK)";
        try {
            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1,"%"+(String) queryInfoMap.get("billType")+"%");
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TruckStatus truckStatus = new TruckStatus();
                truckStatus.setTruckStatusId(rs.getString("FENTRYID"));//派车单流转状态内码
                truckStatus.setTruckStatusName(rs.getString("FDATAVALUE"));//派车单流转状态名称
                truckStatuses.add(truckStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return truckStatuses;
    }
    @Override
    public List queryTruckBillByCondition(HashMap<String, String> queryInfoMap) {
        List<TruckBillBean> truckBillBeans = new ArrayList<>();
        conn = ConnectionPool.getConn();
        String sql = "SELECT FBILLNO,FWBTIME1,T_BD_MATERIAL_L.Fname FMATERIALNAME,T_BAS_BILLTYPE_L.FNAME FBILLTYPENAME,T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE FTRUCKSTATUS,FDRIVER,FCARNUM,FWBQTY1,FQTY \n" +
                "FROM T_SD_ZNTRUCK LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID " +
                "LEFT JOIN T_BAS_ASSISTANTDATAENTRY_L ON T_SD_ZNTRUCK.FPCDLZ = T_BAS_ASSISTANTDATAENTRY_L.FENTRYID left join T_BAS_BILLTYPE_L on T_BAS_BILLTYPE_L.FBILLTYPEID = T_SD_ZNTRUCK.FBILLTYPEID " +
                "WHERE FCLOSESTATUS = 'A'";
        /**
         * FBILLNO,FWBTIME1,FMATERIALNAME,FTRUCKSTATUS,FDRIVER,FCARNUM,FWBQTY1,FQTY
         *
         * */
        int count[] = {0,0,0};

        String truckStatus = queryInfoMap.get("truckStatus");
        if(truckStatus != null && !truckStatus.isEmpty() && !"".equals(truckStatus) ){
            sql = sql + "AND T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE LIKE ? ";
            count[0] = 1;
        }
        String materialName = queryInfoMap.get("materialName");
        if(materialName != null && !materialName.isEmpty() && !"".equals(materialName) ){
            sql = sql + "AND T_BD_MATERIAL_L.Fname like ? ";
            count[1] = 1;
        }
        String billType = queryInfoMap.get("billType");
        if(billType != null && !billType.isEmpty() && !"".equals(billType) ){
            sql = sql + "AND T_BAS_BILLTYPE_L.FNAME like ? ";
            count[2] = 1;
        }
        sql = sql + " order by FWBTIME1";
        try {
            pstmt = conn.prepareStatement(sql);
            int i = 1;
            if(count[0]>0) {
                pstmt.setString(i, "%" + (String) queryInfoMap.get("truckStatus") + "%");
                i++;
            }
            if(count[1]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("materialName") + "%");
                i++;
            }
            if(count[2]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("billType") + "%");
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TruckBillBean truckBillBean = new TruckBillBean();
                truckBillBean.setBillNo(rs.getString("FBILLNO"));//获取单据编号
                String nowTime = rs.getString("FWBTIME1");
                if(nowTime != null && !"".equals(nowTime) && !nowTime.isEmpty()){
//                    System.out.println("获取到的一次称重时间为:"+nowTime);
                    nowTime = nowTime.substring(0,19);
//                    System.out.println("获取到的修改后的一次称重时间为:"+nowTime);
                }
                truckBillBean.setWbTime1(nowTime);//获取一次称重时间
                truckBillBean.setCarStatus(rs.getString("FTRUCKSTATUS"));//获取车辆状态
                truckBillBean.setMaterialName(rs.getString("FMATERIALNAME"));//获取物料名称
                truckBillBean.setBillType(rs.getString("FBILLTYPENAME"));//获取单据类型
                truckBillBean.setDriver(rs.getString("FDRIVER"));//获取司机
                truckBillBean.setCarNum(rs.getString("FCARNUM"));//获取车牌号
                truckBillBeans.add(truckBillBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return truckBillBeans;
    }
    @Override
    public int checkTruckBillByCondition(HashMap<String, String> queryInfoMap) {
//        List<TruckBillBean> truckBillBeans = new ArrayList<>();
        conn = ConnectionPool.getConn();
        int sumCount = 0;//校验获取获取的目标行记录条数
        String sql = "Select count(FBIllNO) sumCount FROM T_SD_ZNTRUCK LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID " +
                "LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID " +
                "LEFT JOIN T_BAS_ASSISTANTDATAENTRY_L ON T_SD_ZNTRUCK.FPCDLZ = T_BAS_ASSISTANTDATAENTRY_L.FENTRYID " +
                "left join T_BAS_BILLTYPE_L on T_BAS_BILLTYPE_L.FBILLTYPEID = T_SD_ZNTRUCK.FBILLTYPEID WHERE FCLOSESTATUS = 'A' ";
        /**
         * FBILLNO,FWBTIME1,FMATERIALNAME,FTRUCKSTATUS,FDRIVER,FCARNUM,FWBQTY1,FQTY
         *
         * */
        int count[] = {0,0,0};

        String truckStatus = queryInfoMap.get("truckStatus");
        if(truckStatus != null && !truckStatus.isEmpty() && !"".equals(truckStatus) ){
            sql = sql + "AND T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE LIKE ? ";
            count[0] = 1;
        }
        String materialName = queryInfoMap.get("materialName");
        if(materialName != null && !materialName.isEmpty() && !"".equals(materialName) ){
            sql = sql + "AND T_BD_MATERIAL_L.Fname like ? ";
            count[1] = 1;
        }
        String billType = queryInfoMap.get("billType");
        if(billType != null && !billType.isEmpty() && !"".equals(billType) ){
            sql = sql + "AND T_BAS_BILLTYPE_L.FNAME like ? ";
            count[2] = 1;
        }
        try {
            pstmt = conn.prepareStatement(sql);
            int i = 1;
            if(count[0]>0) {
                pstmt.setString(i, "%" + (String) queryInfoMap.get("truckStatus") + "%");
                i++;
            }
            if(count[1]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("materialName") + "%");
                i++;
            }
            if(count[2]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("billType") + "%");
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
//               TruckBillBean truckBillBean = new TruckBillBean();
                int checkCount = rs.getInt("sumCount");//获取单据编号
                if(checkCount >0){
                    sumCount = 1;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return sumCount;
    }
    @Override
    public List queryTruckBillByConditionAndPageCount(HashMap<String, String> queryInfoMap) {
        List<TruckBillBean> truckBillBeans = new ArrayList<>();
        conn = ConnectionPool.getConn();
        String sql = "SELECT FBILLNO,FWBTIME1,T_BD_MATERIAL_L.Fname FMATERIALNAME,T_BAS_BILLTYPE_L.FNAME FBILLTYPENAME,T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE FTRUCKSTATUS,FDRIVER,FCARNUM,FWBQTY1,FQTY \n" +
                "FROM T_SD_ZNTRUCK LEFT JOIN T_SD_ZNTRUCKENTRY ON T_SD_ZNTRUCK.FID = T_SD_ZNTRUCKENTRY.FID LEFT JOIN T_BD_MATERIAL_L ON T_SD_ZNTRUCK.FMATERIALID = T_BD_MATERIAL_L.FMATERIALID " +
                "LEFT JOIN T_BAS_ASSISTANTDATAENTRY_L ON T_SD_ZNTRUCK.FPCDLZ = T_BAS_ASSISTANTDATAENTRY_L.FENTRYID left join T_BAS_BILLTYPE_L on T_BAS_BILLTYPE_L.FBILLTYPEID = T_SD_ZNTRUCK.FBILLTYPEID " +
                "WHERE FCLOSESTATUS = 'A' ";
        /**
         * FBILLNO,FWBTIME1,FMATERIALNAME,FTRUCKSTATUS,FDRIVER,FCARNUM,FWBQTY1,FQTY
         *
         * */
        int count[] = {0,0,0};

        String truckStatus = queryInfoMap.get("truckStatus");
        if(truckStatus != null && !truckStatus.isEmpty() && !"".equals(truckStatus) ){
            sql = sql + "AND T_BAS_ASSISTANTDATAENTRY_L.FDATAVALUE LIKE ? ";
            count[0] = 1;
        }
        String materialName = queryInfoMap.get("materialName");
        if(materialName != null && !materialName.isEmpty() && !"".equals(materialName) ){
            sql = sql + "AND T_BD_MATERIAL_L.Fname like ? ";
            count[1] = 1;
        }
        String billType = queryInfoMap.get("billType");
        if(billType != null && !billType.isEmpty() && !"".equals(billType) ){
            sql = sql + "AND T_BAS_BILLTYPE_L.FNAME like ? ";
            count[2] = 1;
        }
        try {
            pstmt = conn.prepareStatement(sql);
            int i = 1;
            if(count[0]>0) {
                pstmt.setString(i, "%" + (String) queryInfoMap.get("truckStatus") + "%");
                i++;
            }
            if(count[1]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("materialName") + "%");
                i++;
            }
            if(count[2]>0){
                pstmt.setString(i, "%" + (String) queryInfoMap.get("billType") + "%");
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TruckBillBean truckBillBean = new TruckBillBean();
                truckBillBean.setBillNo(rs.getString("FBILLNO"));//获取单据编号
                truckBillBean.setWbTime1(rs.getString("FWBTIME1"));//获取一次称重时间
                truckBillBean.setMaterialName(rs.getString("FMATERIALNAME"));//获取物料名称
                truckBillBean.setBillType(rs.getString("FBILLTYPENAME"));//获取单据类型
                truckBillBean.setDriver(rs.getString("FDRIVER"));//获取司机
                truckBillBean.setCarNum(rs.getString("FCARNUM"));//获取车牌号
                truckBillBeans.add(truckBillBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.close(pstmt, rs, conn);
        }
        return truckBillBeans;
    }
}
