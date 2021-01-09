package com.yuxin.system.bean;

public class TruckStatus {
    String truckStatusId;
    String truckStatusName;

    @Override
    public String toString() {
        return "TruckStatus{" +
                "truckStatusId='" + truckStatusId + '\'' +
                ", truckStatusName='" + truckStatusName + '\'' +
                '}';
    }

    public String getTruckStatusId() {
        return truckStatusId;
    }

    public void setTruckStatusId(String truckStatusId) {
        this.truckStatusId = truckStatusId;
    }

    public String getTruckStatusName() {
        return truckStatusName;
    }

    public void setTruckStatusName(String truckStatusName) {
        this.truckStatusName = truckStatusName;
    }
}
