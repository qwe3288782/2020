package com.yuxin.system.bean;

import java.util.Date;

public class TruckBillBean {
    String billNo;//派车单编号
    String billType;//派车单类型
    String materialName;//物料名称
    String driver;//司机
    String carNum;//车牌
    String carStatus;//车辆状态
    double wbQTY1;//一次称重
    String wbTime1;//一次称重时间
    double QTY;//数量

    String stockName;//仓库名

    @Override
    public String toString() {
        return "TruckBillBean{" +
                "billNo='" + billNo + '\'' +
                ", billType='" + billType + '\'' +
                ", materialName='" + materialName + '\'' +
                ", driver='" + driver + '\'' +
                ", carNum='" + carNum + '\'' +
                ", carStatus='" + carStatus + '\'' +
                ", wbQTY1=" + wbQTY1 +
                ", wbTime1=" + wbTime1 +
                ", QTY=" + QTY +
                ", stockName='" + stockName + '\'' +
                '}';
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public double getWbQTY1() {
        return wbQTY1;
    }

    public void setWbQTY1(double wbQTY1) {
        this.wbQTY1 = wbQTY1;
    }

    public String getWbTime1() {
        return wbTime1;
    }

    public void setWbTime1(String wbTime1) {
        this.wbTime1 = wbTime1;
    }

    public double getQTY() {
        return QTY;
    }

    public void setQTY(double QTY) {
        this.QTY = QTY;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
